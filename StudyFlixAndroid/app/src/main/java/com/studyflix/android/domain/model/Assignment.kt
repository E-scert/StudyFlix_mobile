package com.studyflix.android.domain.model

data class Assignment(
    val id: String,
    val title: String,
    val subject: String,
    val teacherName: String,
    val dueDate: String,
    val totalMarks: Int,
    val status: String,
    val grade: String,
    val schoolId: String,
    val examiner: String,
    val examTime: String,
    val duration: Int,
    val instructions: List<String>,
    val questions: List<AssignmentQuestion>
)

data class AssignmentQuestion(
    val number: Int,
    val text: String
)