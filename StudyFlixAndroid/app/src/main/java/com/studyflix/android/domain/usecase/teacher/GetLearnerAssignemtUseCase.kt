package com.studyflix.android.domain.usecase.teacher

import com.studyflix.android.domain.model.TeacherLearnerAssignment
import com.studyflix.android.domain.repository.TeacherRepository
import javax.inject.Inject

class GetLearnerAssignmentsUseCase @Inject constructor(
    private val repository: TeacherRepository
) {

    suspend operator fun invoke(
        learnerId: String
    ): List<TeacherLearnerAssignment> {

        return repository.getLearnerAssignments(
            learnerId
        )
    }
}