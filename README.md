# Kurir App — KMP + Compose Multiplatform Starter

Starter template aplikasi kurir pakai **Kotlin Multiplatform (KMP)** +
**Compose Multiplatform**. UI-nya ditulis sekali di `commonMain` dan jalan
di Android maupun iOS.

## Struktur project

```
CourierApp/
├─ shared/                     # Modul KMP inti
│  └─ src/
│     ├─ commonMain/kotlin/com/kurir/app/
│     │  ├─ App.kt             # Root composable + navigasi
│     │  ├─ model/              # Order, Courier, OrderStatus
│     │  ├─ data/               # OrderRepository (fake/in-memory)
│     │  └─ ui/
│     │     ├─ theme/           # Warna & tipografi
│     │     └─ screen/          # Login, OrderList, OrderDetail, Tracking
│     ├─ androidMain/           # implementasi actual khusus Android
│     └─ iosMain/               # implementasi actual khusus iOS + MainViewController
├─ androidApp/                 # Entry point Android (MainActivity tipis)
└─ iosApp/                     # Entry point iOS (SwiftUI, bungkus Compose)
```

## Cara jalanin (Android)

1. Buka folder `CourierApp/` ini di **Android Studio** (versi terbaru,
   Koala/Ladybug ke atas, yang sudah support Compose Multiplatform &
   Kotlin 2.0).
2. Pastikan Gradle JDK di **Settings > Build, Execution, Deployment >
   Build Tools > Gradle** diarahkan ke JDK 21 (punya lo: `javac 21.0.9`
   sudah cocok).
3. Biarkan Gradle sync selesai (bakal download dependency Compose
   Multiplatform, AGP 8.5.2, Kotlin 2.0.21, dst).
4. Pilih run configuration `androidApp`, jalankan ke emulator/device.

Data pesanan masih **dummy** (`FakeOrderRepository`, 3 contoh order).
Alur: Login (asal isi apa aja) → Daftar Pesanan → Detail → Tracking.

## Cara jalanin (iOS)

Karena file project Xcode (`.xcodeproj`) itu format khusus yang paling
aman digenerate otomatis, cara paling gampang:

1. Di Android Studio, install plugin **Kotlin Multiplatform** kalau
   belum ada.
2. Jalankan `./gradlew :shared:embedAndSignAppleFrameworkForXcode`
   (atau buka project via wizard KMP yang otomatis bikinin
   `iosApp.xcodeproj`).
3. Kalau mau bikin dari nol: buat project iOS App baru di Xcode dengan
   nama `iosApp`, tambahkan file `iOSApp.swift` dan `ContentView.swift`
   yang sudah disiapkan di folder `iosApp/iosApp/` ini (isinya sudah
   nyambung ke `MainViewController()` dari modul `shared`), lalu link
   framework `shared.framework` hasil build KMP (biasanya lewat
   "Run Script" build phase `embedAndSignAppleFrameworkForXcode`, sama
   seperti template resmi JetBrains KMP wizard).

Referensi resmi: https://kmp.jetbrains.com (wizard buat generate
`iosApp.xcodeproj` otomatis kalau mau langsung lengkap).

## Yang perlu lo lanjutin

- **Ganti `FakeOrderRepository`** dengan implementasi asli yang manggil
  REST API ke backend lo (`Backend-1`). Tinggal implement interface
  `OrderRepository`, misal pakai **Ktor Client** (tambahkan dependency
  `io.ktor:ktor-client-core` dkk di `shared/build.gradle.kts`).
- **Autentikasi**: `LoginScreen` sekarang cuma dummy (langsung sukses).
  Sambungkan ke endpoint login backend lo, simpan token (misal pakai
  `multiplatform-settings` atau `DataStore` untuk Android + Keychain
  untuk iOS).
- **Peta / tracking real-time**: `TrackingScreen` masih placeholder
  kotak abu-abu. Integrasikan Google Maps Compose (Android) / MapKit
  (iOS), atau library KMP seperti `maplibre-compose` kalau mau share
  map component juga.
- **Push notification** buat update status pesanan.
- **Dependency injection**: starter ini sengaja belum pakai DI
  framework (Koin/Kodein) biar sederhana — tinggal `remember { }`.
  Kalau project makin besar, pertimbangkan tambahin Koin.

## Versi yang dipakai

| Tool | Versi |
|---|---|
| Kotlin | 2.0.21 |
| Compose Multiplatform | 1.7.0 |
| AGP | 8.5.2 |
| compileSdk / targetSdk | 35 |
| minSdk | 24 |
| JDK | 21 |
