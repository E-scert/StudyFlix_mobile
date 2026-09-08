package com.studyflix.android.domain.usecase.teacher

import com.studyflix.android.domain.model.TeacherOverview
import com.studyflix.android.domain.repository.TeacherRepository
import javax.inject.Inject

class GetTeacherOverviewUseCase @Inject constructor(
    private val repository: TeacherRepository
) {

    suspend operator fun invoke(
        teacherUid: String
    ): TeacherOverview {

        return repository.getTeacherOverview(
            teacherUid
        )
    }
}