package com.studyflix.android.domain.model

/** Domain model for a document in the `quizzes` collection (status == "published"). */
data class Quiz(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val subject: String = "",
    val grade: String = "",
    val questions: List<QuizQuestion> = emptyList(),
    val totalMarks: Int = 0,
    val timeLimitMinutes: Int = 15
)

/** A single multiple-choice question, matching the shape used by QuizManager on the web. */
data class QuizQuestion(
    val text: String = "",
    val options: List<String> = emptyList(),
    val correctIndex: Int = 0,
    val marks: Int = 2
)

/** Represents the student's in-progress or completed attempt at a quiz. */
data class QuizAttempt(
    val quizId: String = "",
    val answers: List<Int?> = emptyList(), // null = unanswered
    val currentQuestionIndex: Int = 0
) {
    val isComplete: Boolean get() = answers.none { it == null }
}
