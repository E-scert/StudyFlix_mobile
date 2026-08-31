package com.studyflix.android.core.util

/**
 * Single source of truth for Firestore collection names, kept identical to the
 * strings used across the web codebase (see PROJECT_DOCUMENTATION.md section 4 & 10)
 * so both clients read/write the same schema.
 */
object FirestoreCollections {
    const val ADMINS = "admins"
    const val TEACHERS = "teachers"
    const val STUDENTS = "students"
    const val SCHOOLS = "schools"
    const val CONTENT = "content"
    const val QUIZZES = "quizzes"
    const val MARKS = "marks"
    const val MESSAGES = "messages"
}
