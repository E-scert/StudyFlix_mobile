package com.studyflix.android.domain.usecase.student

import com.studyflix.android.domain.model.Student
import com.studyflix.android.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStudentProfileUseCase @Inject constructor(
    private val studentRepository: StudentRepository
) {
    operator fun invoke(uid: String): Flow<Student?> = studentRepository.observeStudent(uid)

    suspend fun refresh(uid: String): Result<Student> = studentRepository.refreshStudent(uid)
}
