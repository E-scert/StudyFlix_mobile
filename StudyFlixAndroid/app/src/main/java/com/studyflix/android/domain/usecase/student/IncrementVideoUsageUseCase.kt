package com.studyflix.android.domain.usecase.student

import com.studyflix.android.domain.repository.StudentRepository
import javax.inject.Inject

class IncrementVideoUsageUseCase @Inject constructor(
    private val repository: StudentRepository
) {

    suspend operator fun invoke() {
        repository.incrementVideosUsed()
    }
}