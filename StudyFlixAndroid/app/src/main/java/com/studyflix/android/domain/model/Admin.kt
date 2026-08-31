package com.studyflix.android.domain.model

/** Domain model for a document in the `admins` Firestore collection. */
data class Admin(
    val uid: String = "",
    val email: String = "",
    val name: String = "",
    val role: String = "admin",
    val status: AccountStatus = AccountStatus.APPROVED,
    val lastLoginMillis: Long? = null
)
