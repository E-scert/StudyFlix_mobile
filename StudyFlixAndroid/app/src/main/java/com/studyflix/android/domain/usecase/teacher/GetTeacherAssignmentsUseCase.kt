package com.studyflix.android.domain.usecase.teacher

import com.studyflix.android.domain.model.TeacherAssignment
import com.studyflix.android.domain.repository.TeacherRepository
import javax.inject.Inject

class GetTeacherAssignmentsUseCase @Inject constructor(
    private val repository: TeacherRepository
) {

    suspend operator fun invoke(
        teacherUid: String
    ): List<TeacherAssignment> {

        return repository.getAssignments(
            teacherUid
        )
    }
}