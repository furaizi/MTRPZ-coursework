# 📱 URL Shortener Mobile Client

[![Android CI](https://github.com/furaizi/MTRPZ-coursework/actions/workflows/ci.yml/badge.svg)](https://github.com/furaizi/MTRPZ-coursework/actions/workflows/ci.yml)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9%2B-blueviolet?logo=kotlin)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-green?logo=android)](https://developer.android.com/jetpack/compose)

A modern Kotlin / Jetpack Compose Android application that consumes the **URL-Shortener** backend and analytics APIs.  
The goal is to provide a seamless mobile experience for creating, managing and analysing short links.

> **Backend repo →** [`furaizi/MTRPZ-lab-4`](https://github.com/furaizi/MTRPZ-lab-4)

---

## ✨ Key Features

| UI / UX | Business |
|---------|----------|
| 🔗 One-tap URL shortening with clipboard integration | ⚡ Fast Retrofit client with Kotlin Coroutines |
| 📊 Inline stats (clicks, unique visitors, last access) | 🔒 Optional API key auth ready |
| 🌗 System Dark / Light theme | 🗑️ Swipe-to-delete with undo |
| 🌍 en / uk localisation | 📈 Deep link to full analytics dashboard |
| 🛠️ Debug drawer (base URL switch, logs) | 🧪 JUnit + Turbine tests |

---

## 🏗️ Tech Stack

| Layer | Libraries / Tools |
|-------|-------------------|
| UI & Navigation | Jetpack Compose (Material 3), Navigation-Compose |
| Dependency Injection | **Hilt** (Dagger 2) |
| Networking | **Retrofit 2**, OkHttp 5, Moshi (with custom `LocalDateTimeAdapter`) |
| Concurrency | Kotlin Coroutines & Flow |
| Architecture | MVVM + Use-Case layer, repositories returning `NetworkResult` and `UiState` |
| Testing | **JUnit 4/5**, AndroidX Instrumentation |
| DevOps | Gradle 8.4, Android Gradle Plugin 8.5, GitHub Actions (lint, unit + instrumented tests, APK artifacts) |

---

## 📸 Screenshots

| Home | Stats |
|------|-------|
| _\[add screenshot\]_ | _\[add screenshot\]_ |

---

## 🚀 Quick Start

1.  **Clone** the repo  
    ```bash
    git clone https://github.com/furaizi/MTRPZ-coursework.git
    cd MTRPZ-coursework
    ```

2.  **Configure backend URL**  
    The app points to `http://10.0.2.2:8080` (Android Emulator) by default.  
    Override via `local.properties` or Debug Drawer ➜ **Base URL**.

3.  **Run**  
    ```bash
    ./gradlew installDebug      # device / emulator connected
    ```

4.  **Backend up & running**  
    Make sure you started the Docker stack from the backend repo:  
    ```bash
    cd ../MTRPZ-lab-4
    docker compose up -d
    ```

---

## 🧪 Tests

```bash
# Unit + instrumentation tests
./gradlew testDebugUnitTest connectedCheck
```

---

## 🛠️ CI / CD
File: .github/workflows/{ci,release}.yml  
Artifacts	Upload *-debug.apk and *-release-unsigned.apk  
Download artefacts from the Actions → run → Summary tab.  

---

## 📁 Project Structure
```bash
app/
 ├─ src/
 │   ├─ main/
 │   │   ├─ kotlin/com.furaizi.app/
 │   │   │   ├─ presentation/   ← Compose screens
 │   │   │   ├─ data/           ← DTOs, Retrofit service
 │   │   │   ├─ domain/         ← Use-cases & models
 │   │   │   ├─ util/           ← State & result types           
 │   │   │   └─ di/             ← Koin modules
 │   │   └─ res/                ← M3 theming, translations
 │   └─ test/                   ← JUnit 5 unit tests
 └─ build.gradle.kts
```

## 🔌 API Client Example
```kotlin
interface LinkApi {
    @POST("links")
    suspend fun create(@Body request: CreateLinkRequest): LinkResponse

    @GET("links/{shortCode}")
    suspend fun details(@Path("shortCode") shortCode: String): LinkResponse

    @GET("links/{shortCode}/stats")
    suspend fun stats(@Path("shortCode") shortCode: String): LinkStatistics

    @DELETE("links/{shortCode}")
    suspend fun delete(@Path("shortCode") shortCode: String): Response<Unit>
}
```

---

Happy shortening! 🔗
