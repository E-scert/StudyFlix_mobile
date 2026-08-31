package com.studyflix.android.domain.usecase.student

import com.studyflix.android.core.util.Resource
import com.studyflix.android.domain.model.Quiz
import com.studyflix.android.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuizzesUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {
    operator fun invoke(): Flow<Resource<List<Quiz>>> = quizRepository.observePublishedQuizzes()
}
