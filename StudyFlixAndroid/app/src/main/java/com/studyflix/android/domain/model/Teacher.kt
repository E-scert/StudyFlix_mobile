package com.studyflix.android.domain.model

/** Domain model for a document in the `teachers` Firestore collection. */
data class Teacher(
    val uid: String = "",
    val email: String = "",
    val name: String = "",
    val school: String = "",
    val province: String = "",
    val gradesTaught: List<String> = emptyList(),
    val subjectsOffered: List<String> = emptyList(),
    val status: AccountStatus = AccountStatus.PENDING
)
