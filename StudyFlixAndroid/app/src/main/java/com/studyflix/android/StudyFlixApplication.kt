package com.studyflix.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/** Hilt entry point; also where FirebaseApp auto-initializes from google-services.json. */
@HiltAndroidApp
class StudyFlixApplication : Application()
