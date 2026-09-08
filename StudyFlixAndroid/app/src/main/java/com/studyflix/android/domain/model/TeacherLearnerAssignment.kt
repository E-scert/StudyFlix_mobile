package com.studyflix.android.domain.model

data class TeacherLearnerAssignment(

    val assignmentId: String = "",

    val title: String = "",

    val dueDate: String = "",

    val submitted: Boolean = false,

    val score: String = ""
)