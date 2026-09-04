package com.studyflix.android.domain.model

data class AssignmentSubmission(
    val assignmentId: String,
    val studentId: String,
    val submittedAt: Long,
    val answers: Map<String, String>,
    val isMarked: Boolean = false,
    val score: Int = 0,
    val feedback: String = "",
    val submissionId: String = "",
    val assignmentTitle: String = "",
    val studentName: String = "",
    val totalMarks: Int = 0,
    val percentage: Int = 0,

    val markedBy: String = "",
    val markedAt: Long = 0,
    val startedAt: Long = 0,
    val questionMarks: Map<String, Int> = emptyMap(),
    val questionFeedback: Map<String, String> = emptyMap(),
    val markRecordCreated: Boolean = false,
)