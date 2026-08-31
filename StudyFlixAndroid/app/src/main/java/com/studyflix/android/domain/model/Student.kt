package com.studyflix.android.domain.model

/**
 * Domain model for a document in the `students` Firestore collection.
 * Field names match public/student/js/auth.js `loadUserData()` exactly so
 * that the Android app and the existing web app stay data-compatible.
 */
data class Student(
    val uid: String = "",
    val email: String = "",
    val name: String = "",
    val subscription: String = "trial",       // "trial" | "active" | "expired" ...
    val trialEnds: String = "",                // ISO-8601 string, matches web
    val grade: String = "Grade 8",
    val school: String = "",
    val schoolId: String = "",
    val status: AccountStatus = AccountStatus.PENDING,
    val completedQuizzes: List<String> = emptyList(),
    val createdAtMillis: Long? = null
)

enum class AccountStatus {
    PENDING, APPROVED, SUSPENDED;

    companion object {
        fun fromRaw(raw: String?): AccountStatus = when (raw) {
            "approved", "active" -> APPROVED
            "suspended", "blocked" -> SUSPENDED
            else -> PENDING
        }
    }
}
