package com.studyflix.android.domain.repository

import com.studyflix.android.core.util.Resource
import com.studyflix.android.domain.model.Quiz
import kotlinx.coroutines.flow.Flow

/** Published quizzes, matching public/student/js/quizzes.js loadQuizzes(). */
interface QuizRepository {
    fun observePublishedQuizzes(): Flow<Resource<List<Quiz>>>

    suspend fun getQuiz(quizId: String): Quiz?

    /** Persists a completed attempt and appends the quiz id to the student's completedQuizzes. */
    suspend fun submitQuizAttempt(
        studentUid: String,
        quiz: Quiz,
        answers: List<Int?>
    ): Result<Int> // returns the score achieved
}
