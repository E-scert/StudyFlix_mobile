package com.studyflix.android.domain.model

/** Domain model for a document in the `schools` Firestore collection. */
data class School(
    val id: String = "",
    val name: String = "",
    val province: String = "",
    val address: String = "",
    val status: String = "pending" // "active" | "trial" | "pending"
)
