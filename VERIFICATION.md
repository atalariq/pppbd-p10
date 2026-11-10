# Validasi implementasi P10

## Pemeriksaan yang sudah dilakukan

- Modul lokal dibaca dan dicocokkan dengan implementasi: Kotlin, minimum API 24, ViewBinding, tiga dependensi dengan versi dari modul, permission INTERNET, model Users/Data, ApiService/ApiClient, GET employees, enqueue, dan ArrayAdapter/ListView.
- Endpoint `https://dummy.restapiexample.com/api/v1/employees` berhasil dibaca melalui alat web pada sesi pengerjaan; respons berisi `status: success` dan record dengan ID, nama, gaji, umur, serta profile image. Ini pemeriksaan endpoint, bukan bukti request dari emulator.
- Dua tes JUnit `EmployeeResponseTest` berhasil dijalankan secara terpisah dari Gradle: pemetaan seluruh field employee serta envelope error tanpa data. Eksekusi memakai compiler Kotlin 2.2.10, JUnit 4.13.2, dan Gson 2.11.0 yang tersedia di cache. Ini pemeriksaan terbatas; dependency graph aplikasi dari converter-gson 2.6.2 belum diverifikasi oleh Gradle.
- Seluruh 13 XML dalam `app/src/main` berhasil diparse.
- `git diff --check` tidak menemukan kesalahan whitespace.
- Helper impor diuji pada clone sementara dengan salinan perubahan `.idea` milik pengguna: kelima commit berhasil diterapkan, tree commit cocok dengan implementasi, dan perubahan staged/unstaged/untracked kembali persis seperti sebelum impor.
- Git bundle berhasil diverifikasi menggunakan `git bundle verify`.

## Yang belum berhasil diverifikasi

Perintah `:app:assembleDebug :app:testDebugUnitTest :app:lintDebug` dicoba dengan Gradle 9.6.0 dan JDK 25 yang tersedia, tetapi Gradle berhenti sebelum konfigurasi/kompilasi:

```text
Could not create service of type FileLockContentionHandler
Could not determine a usable wildcard IP for this machine.
```

Sandbox membatasi socket/jaringan lokal. `adb devices -l` juga gagal membuka listener dengan `Operation not permitted`. Karena itu APK, lint, tes Gradle, dan demo emulator belum dinyatakan lulus. Tes `DetailActivityTest` sudah disiapkan, tetapi belum dijalankan.

## Jalankan di lingkungan Android Studio pengguna

1. Terapkan patch ke proyek asli atau buka salinan implementasi ini sebagai proyek Android Studio.
2. Jalankan Gradle Sync. Pertahankan SDK/JDK proyek yang sudah ada: compile/target API 37, min API 24, Gradle 9.6.0, dan daemon JDK 25.
3. Jalankan dari terminal dengan Java yang sesuai tersedia pada PATH:

   ```bash
   ./gradlew :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
   adb devices -l
   ./gradlew :app:connectedDebugAndroidTest
   ```

4. Run aplikasi `debug` pada emulator yang terhubung internet. Pastikan daftar nama tampil dari API aktual.
5. Klik satu nama: cocokkan ID, nama, umur, dan gaji dengan record respons. Gunakan Back, kemudian klik nama lain dan pastikan detailnya berubah sesuai pilihan.
6. Putar layar saat membuka detail; pastikan keempat nilai tetap tampil. Saat kembali ke list, tunggu request selesai bila activity dibuat ulang.
7. Buka ulang aplikasi dengan internet emulator dimatikan: pastikan muncul toast “Koneksi error” tanpa crash. Aktifkan kembali internet dan buka ulang aplikasi sebelum merekam demo.

Tidak ada data fallback lokal, tombol retry, pencarian, maupun operasi tambah/edit/hapus. Kesalahan HTTP, body kosong/tanpa data, dan status API selain success diperlakukan sebagai kegagalan. Detail memakai data yang sudah didapat dari list tanpa request kedua.
