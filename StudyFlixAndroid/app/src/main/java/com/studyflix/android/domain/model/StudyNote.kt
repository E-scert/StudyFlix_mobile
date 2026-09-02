package com.studyflix.android.domain.model

data class StudyNote(
    val id: String,
    val title: String,
    val subject: String,
    val topic: String,
    val description: String,
    val content: String,
    val authorName: String,
    val fileUrl: String = "",
    val teacherSchool: String? = null,
    val uploadedBy: String? = null
)