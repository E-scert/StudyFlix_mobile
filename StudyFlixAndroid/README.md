# StudyFlix Android

Native Android rewrite of the StudyFlix Firebase web app (see
`PROJECT_DOCUMENTATION.md` / `README.md` from the web repo), built with:

- **Kotlin + Jetpack Compose** (no XML layouts)
- **MVVM** — `ui/*ViewModel.kt` classes expose `StateFlow<UiState>` to Compose screens
- **Clean Architecture** — `domain` (models, repository interfaces, use cases) is
  fully independent of `data` (Firebase + Room) and `ui` (Compose + ViewModels)
- **Hilt** for dependency injection (`di/`)
- **Firebase Auth / Firestore / Storage / Functions** — same backend and
  collection schema as the web app (`admins`, `teachers`, `students`,
  `schools`, `content`, `quizzes`, `marks`, `messages`)
- **Room** as an offline-first local cache, mirroring the web app's
  `db.enablePersistence()` behaviour

## Package map

```
com.studyflix.android
├── StudyFlixApplication.kt        # @HiltAndroidApp entry point
├── MainActivity.kt                # single Activity, hosts Compose NavHost
├── di/                            # Hilt modules
│   ├── FirebaseModule.kt          # FirebaseAuth / Firestore / Storage / Functions
│   ├── DatabaseModule.kt          # Room database + DAOs
│   └── RepositoryModule.kt        # binds domain interfaces -> data impls
├── core/
│   ├── util/
│   │   ├── Resource.kt            # Loading/Success/Error wrapper
│   │   ├── NetworkBoundResource.kt# offline-first Flow helper
│   │   └── FirestoreCollections.kt
│   ├── navigation/                # Screen.kt (routes) + NavGraph.kt
│   └── ui/theme/                  # Color.kt, Theme.kt, Type.kt
├── domain/                        # pure Kotlin, no Android/Firebase imports
│   ├── model/                     # Student, Teacher, Admin, School, VideoContent,
│   │                               # Quiz, QuizQuestion, Mark, ChatMessage, UserRole
│   ├── repository/                # interfaces only
│   └── usecase/
│       ├── auth/                  # SignIn, SignUpStudent, ObserveAuthState, SignOut
│       └── student/                # GetVideos, GetQuizzes, SubmitQuiz, GetMarks, Chat*
├── data/
│   ├── repository/                # *RepositoryImpl — Firebase + Room
│   └── local/                     # Room: StudyFlixDatabase, dao/, entity/
└── ui/
    ├── auth/                      # LoginScreen + LoginViewModel, SignUpStudentScreen + VM
    ├── student/
    │   ├── home/ videos/ quizzes/ marks/ chat/   # one VM + one Screen per feature
    ├── teacher/                   # TeacherDashboardScreen (scaffold, same pattern)
    └── admin/                     # AdminDashboardScreen (scaffold, same pattern)
```

## Why this shape

- **Role resolution** (`SignInUseCase`, `AuthRepositoryImpl.getUserRole`) probes
  `admins` → `teachers` → `students` in that exact order, matching
  `getUserRole()` in `public/shared/role-manager.js`, so a user who somehow has
  documents in multiple collections resolves to the same portal on both clients.
- **Student sign-up** (`SignUpStudentUseCase` → `AuthRepositoryImpl.signUpStudent`)
  creates the same default document shape as the "create new user document"
  branch of `StudentAuth.loadUserData()` on web: `status: "pending"`,
  `subscription: "trial"`, a 30-day `trialEnds`, and an empty `completedQuizzes`.
- **Videos / Quizzes / Marks** repositories issue the exact same Firestore
  queries as `VideoManager`, `QuizManager`, and `MarksManager` on web
  (`where("status","==","approved"/"published")`, `where("studentId","==",uid)`,
  etc.), then cache results in Room so the app works offline.
- **Chat** stays a live Firestore `addSnapshotListener` (not Room) since it's
  an inherently real-time stream; Firestore's own persistent cache already
  covers offline reads for it.
- **Teacher/Admin portals** are intentionally scaffolded rather than fully
  built out: they follow the *exact same* Repository → UseCase → ViewModel →
  Compose Screen pattern demonstrated in full for the student portal. Extending
  them is a matter of adding `TeacherRepository`/`AdminRepository` interfaces
  + impls and the matching use cases, listed as comments inside
  `TeacherDashboardScreen.kt` / `AdminDashboardScreen.kt`.

## Setup

1. Create/open an Android project pointing at the **same Firebase project**
   the web app uses: `studyflix-1c5e5` (see `.firebaserc`).
2. In the Firebase console, add an Android app with package name
   `com.studyflix.android`, download `google-services.json`, and place it at
   `app/google-services.json`.
3. Open in Android Studio (Ladybug+) and let Gradle sync — all dependency
   versions are centralized in `gradle/libs.versions.toml`.
4. Run on a device/emulator with Google Play services.

## Notes / next steps

- Firestore security rules referenced as a gap in `PROJECT_DOCUMENTATION.md`
  section 13 should be added regardless of client (web or Android) — client-side
  role checks are a UX convenience, not a security boundary.
- The Cloud Function `generateSchoolCode` (`functions/index.js`) is unchanged;
  call it from Android via `firebase-functions-ktx`
  (`FirebaseFunctions.getInstance().getHttpsCallable("generateSchoolCode")`).
- Push notifications for chat (`ChatRepositoryImpl`) are not wired up here —
  add `firebase-messaging-ktx` + a Cloud Function trigger on new `messages`
  docs when needed.
