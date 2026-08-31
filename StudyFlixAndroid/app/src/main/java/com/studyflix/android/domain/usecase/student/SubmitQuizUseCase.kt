package com.studyflix.android.domain.usecase.student

import com.studyflix.android.domain.repository.QuizRepository
import javax.inject.Inject

class SubmitQuizUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {
    suspend operator fun invoke(studentUid: String, quizId: String, answers: List<Int?>): Result<Int> {
        val quiz = quizRepository.getQuiz(quizId)
            ?: return Result.failure(IllegalStateException("Quiz not found."))
        return quizRepository.submitQuizAttempt(studentUid, quiz, answers)
    }
}
