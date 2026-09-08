package com.studyflix.android.domain.model

data class Teacher(
    val uid: String = "",

    val email: String = "",

    val name: String = "",

    val phone: String = "",

    val role: String = "teacher",

    val schoolId: String = "",
    val schoolName: String = "",
    val schoolCode: String = "",

    val grade: String = "",
    val selectedGrade: String = "",

    val subject: String = "",
    val selectedSubject: String = "",

    val grades: List<String> = emptyList(),
    val subjects: List<String> = emptyList(),

    val subscription: String = "trial",

    val status: AccountStatus = AccountStatus.PENDING
)