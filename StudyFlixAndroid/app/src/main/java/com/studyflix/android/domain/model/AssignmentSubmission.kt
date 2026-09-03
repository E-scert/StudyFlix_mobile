package com.studyflix.android.domain.model

data class AssignmentSubmission(
    val assignmentId: String,
    val studentId: String,
    val submittedAt: Long,
    val answers: Map<String, String>
)