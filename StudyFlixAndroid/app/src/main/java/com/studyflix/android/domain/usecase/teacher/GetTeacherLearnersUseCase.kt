package com.studyflix.android.domain.usecase.teacher

import com.studyflix.android.domain.model.TeacherLearner
import com.studyflix.android.domain.repository.TeacherRepository
import javax.inject.Inject

class GetTeacherLearnersUseCase @Inject constructor(
    private val repository: TeacherRepository
) {

    suspend operator fun invoke(
        teacherUid: String
    ): List<TeacherLearner> {

        return repository.getTeacherLearners(
            teacherUid
        )
    }
}