#!/usr/bin/env bash
# Import exported atomic commits while preserving pre-existing staged/unstaged work.
set -euo pipefail

if [[ $# -ne 1 ]]; then
    echo "Usage: bash $0 /absolute/path/to/original/PPPBD-P10-Konekin" >&2
    exit 2
fi
script_dir=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)
patch_dir="$script_dir/../../patches"
target=$(cd -- "$1" && pwd -P)
base=2f8b5fb6895587b7d7d4f50d63a8cf4d02e89b65

if [[ $(git -C "$target" rev-parse --show-toplevel) != "$target" ]]; then
    echo "Target must be the original repository root." >&2
    exit 1
fi
if [[ $(git -C "$target" rev-parse HEAD) != "$base" ]]; then
    echo "Target HEAD differs from the original base; inspect its history before importing." >&2
    exit 1
fi
if [[ -d $(git -C "$target" rev-parse --absolute-git-dir)/rebase-apply ||
      -d $(git -C "$target" rev-parse --absolute-git-dir)/rebase-merge ||
      -f $(git -C "$target" rev-parse --absolute-git-dir)/MERGE_HEAD ]]; then
    echo "Finish the existing Git operation before importing." >&2
    exit 1
fi
shopt -s nullglob
patches=("$patch_dir"/*.patch)
if [[ ${#patches[@]} -eq 0 ]]; then
    echo "Exported patches not found at $patch_dir" >&2
    exit 1
fi
stash_oid=
if [[ -n $(git -C "$target" status --porcelain --untracked-files=all) ]]; then
    git -C "$target" stash push --include-untracked -m 'Backup before P10 implementation import'
    stash_oid=$(git -C "$target" rev-parse refs/stash)
    echo "Existing work backed up as $stash_oid"
fi
if ! git -C "$target" -c commit.gpgsign=false am "${patches[@]}"; then
    echo "Import stopped. Inspect git status; use git am --continue or git am --abort." >&2
    echo "Existing work remains in stash: ${stash_oid:-no backup was needed}" >&2
    exit 1
fi
if [[ -n $stash_oid ]]; then
    if ! git -C "$target" stash apply --index "$stash_oid"; then
        echo "Commits imported; restoring previous work needs conflict resolution." >&2
        echo "Backup remains in stash: $stash_oid" >&2
        exit 1
    fi
    echo "Previous staged/unstaged work restored. Backup retained in stash: $stash_oid"
fi
git -C "$target" log --oneline "$base"..HEAD
echo 'Import complete. Open the original project in Android Studio and run Gradle Sync.'
