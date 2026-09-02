package com.studyflix.android.ui.student.notes

data class Note(
    val id: String,
    val title: String,
    val subject: String,
    val topic: String,
    val author: String,
    val description: String,
    val content: String,
    val teacherSchool: String? = null,
    val uploadedBy: String? = null
)