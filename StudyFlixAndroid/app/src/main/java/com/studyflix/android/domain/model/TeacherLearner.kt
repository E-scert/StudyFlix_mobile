package com.studyflix.android.domain.model

data class TeacherLearner(

    val id: String = "",

    val name: String = "",

    val email: String = "",

    val phone: String = "",

    val school: String = "",

    val schoolId: String = "",

    val grade: String = "",

    val plan: String = "",

    val joinedDate: Long? = null,

    val averageScore: Int = 0,

    val submissionsCount: Int = 0,

    val online: Boolean = false
)
