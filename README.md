# StudyFlix Mobile

StudyFlix Mobile is a native Android application designed to provide students, teachers, and administrators with a comprehensive e-learning platform. It is a modern rewrite of the StudyFlix web application, leveraging Firebase for real-time data and authentication, and Room for offline capabilities.

## System Overview

The system is built as a role-based portal where users access different functionalities based on their profile:

### 🎓 Student Portal
The primary interface for learners, offering:
- **Video Learning:** High-quality video content with an integrated player (Media3 ExoPlayer).
- **Interactive Quizzes:** Real-time assessment tools with instant feedback and score tracking.
- **Assignments:** A dedicated module for viewing assignment details, answering questions with timed sessions, and tracking submission status.
- **Study Materials:** Access to notes and past papers for offline and online study.
- **Performance Tracking:** A "Marks" screen to monitor academic progress across subjects.
- **Communication:** Integrated chat system for interacting with teachers and peers.

### 👩‍🏫 Teacher Portal
Provides dashboards for managing educational content, monitoring student progress, and interacting with classes. (Currently scaffolded for further expansion).

### 🛠️ Admin Portal
A high-level management interface for system oversight, user management, and school-wide settings.

## Technical Architecture

The application follows **Modern Android Development (MAD)** practices:

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose (Declarative UI)
- **Architecture:** Clean Architecture with MVVM (Model-View-ViewModel).
    - **Domain Layer:** Pure Kotlin models and repository interfaces.
    - **Data Layer:** Firebase (Auth, Firestore) and Room Database for offline-first synchronization.
    - **UI Layer:** Compose screens observing StateFlow from Hilt-injected ViewModels.
- **Dependency Injection:** Hilt (Dagger)
- **Networking/Database:** 
    - **Firebase Firestore:** Primary cloud database.
    - **Room:** Local persistent cache for offline access to videos, quizzes, and marks.
    - **Coil:** Efficient image loading.
- **Navigation:** Type-safe Compose Navigation.

## Project Structure

- `StudyFlixAndroid/`: The main Android Studio project directory.
    - `app/src/main/java/com/studyflix/android/ui/`: Contains role-specific UI modules (student, teacher, admin).
    - `app/src/main/java/com/studyflix/android/domain/`: Core business logic and entities.
    - `app/src/main/java/com/studyflix/android/data/`: Implementation of repositories and local database.
- `docs/`: Development logs, progress reports, and feature specifications.

## Setup & Development

1. **Firebase Configuration:** Place your `google-services.json` in the `app/` directory.
2. **Environment:** Requires Android Studio Ladybug+ and Kotlin 2.x support.
3. **Build:** Use `./gradlew assembleDebug` to build the application.

StudyFlix Mobile ensures that the learning experience is seamless, even with intermittent connectivity, providing a robust tool for modern education.
