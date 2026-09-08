package com.studyflix.android.domain.usecase.teacher

import com.studyflix.android.domain.repository.TeacherRepository
import javax.inject.Inject

class CreateAssignmentUseCase @Inject constructor(
    private val repository: TeacherRepository
) {

    suspend operator fun invoke(
        teacherUid: String,
        title: String,
        subject: String,
        totalMarks: Int,
        dueDate: String
    ) {

        repository.createAssignment(
            teacherUid = teacherUid,
            title = title,
            subject = subject,
            totalMarks = totalMarks,
            dueDate = dueDate
        )
    }
}