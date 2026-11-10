# Implementasi P10 — Konekin

Implementasi mengikuti `materials/p10-retrofit/module.local.pdf`: ViewBinding, permission INTERNET, model `Users`/`Data`, `ApiClient`, `ApiService`, dan list nama employee melalui Retrofit + Gson. Dependensi memakai versi modul. Halaman detail menampilkan ID, nama, umur, dan gaji dari record list yang diklik, melalui Intent tanpa request baru. Navigasi kembali menggunakan Back sistem.

- Panduan narasi dan aksi layar video 5–7 menit: [VIDEO_SCRIPT.md](VIDEO_SCRIPT.md).
- Hasil pemeriksaan serta langkah build/demo: [VERIFICATION.md](VERIFICATION.md).

## Lokasi kerja dan penerapan ke proyek asli

Proyek asli berada di `code/PPPBD-P10-Konekin`, melalui symlink menuju `/home/atalariq/AndroidStudioProjects/PPPBD-P10-Konekin`. Lokasi tersebut read-only dalam sesi agen. Implementasi dan seluruh commit disiapkan pada salinan terpisah di `p10-implementation/PPPBD-P10-Konekin`; proyek asli belum berubah.

Paket distribusi menyediakan `../patches/*.patch`. Jalankan perintah berikut **dari terminal pengguna di luar sandbox agen** untuk menerapkan commit ke proyek yang sudah dibuka di Android Studio:

```bash
bash /home/atalariq/Works/pppbd-laprak-dan-video/p10-implementation/PPPBD-P10-Konekin/tools/apply-to-original.sh \
  /home/atalariq/AndroidStudioProjects/PPPBD-P10-Konekin
```

Script memeriksa HEAD awal, menyimpan perubahan staged/unstaged dan file untracked ke stash bila diperlukan, menerapkan rangkaian commit menggunakan `git am`, lalu mengembalikan perubahan awal beserta status staged-nya. Backup stash sengaja disimpan untuk pemeriksaan pengguna. Jika terjadi konflik, script berhenti dan menyebutkan lokasi backup; jangan menghapus stash sebelum memastikan perubahan awal pulih.

Commit dibuat tanpa tanda tangan karena kunci signing pengguna memerlukan passphrase yang tidak tersedia pada sesi ini. Script impor juga memakai commit tanpa tanda tangan. Tidak ada push ke remote.

Alternatifnya, buka langsung salinan implementasi ini di Android Studio. Untuk distribusi yang tidak menyertakan direktori Git, tersedia bundle di `../p10-implementation.bundle`.
