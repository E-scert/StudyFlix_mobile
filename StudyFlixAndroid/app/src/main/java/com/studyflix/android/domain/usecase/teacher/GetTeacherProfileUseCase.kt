package com.studyflix.android.domain.usecase.teacher

import com.studyflix.android.domain.model.Teacher
import com.studyflix.android.domain.repository.TeacherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTeacherProfileUseCase @Inject constructor(
    private val teacherRepository: TeacherRepository
) {

    operator fun invoke(
        uid: String
    ): Flow<Teacher?> {

        return teacherRepository.observeTeacher(uid)
    }

    suspend fun refresh(
        uid: String
    ): Result<Teacher> {

        return teacherRepository.refreshTeacher(uid)
    }
}