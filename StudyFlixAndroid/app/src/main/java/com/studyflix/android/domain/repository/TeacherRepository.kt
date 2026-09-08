package com.studyflix.android.domain.repository


import com.studyflix.android.domain.model.Teacher
import com.studyflix.android.domain.model.TeacherChatMessage
import com.studyflix.android.domain.model.TeacherLearner
import com.studyflix.android.domain.model.TeacherLearnerAssignment
import com.studyflix.android.domain.model.TeacherOverview
import kotlinx.coroutines.flow.Flow

interface TeacherRepository {

    fun observeTeacher(
        uid: String
    ): Flow<Teacher?>

    suspend fun refreshTeacher(
        uid: String
    ): Result<Teacher>

    suspend fun getTeacher(
        uid: String
    ): Teacher?


    suspend fun getTeacherOverview(
        teacherUid: String
    ): TeacherOverview

    suspend fun getTeacherLearners(
        teacherUid: String
    ): List<TeacherLearner>

    suspend fun getLearnerById(
        learnerId: String
    ): TeacherLearner?

    suspend fun getLearnerAssignments(
        teacherUid: String,
        learnerId: String
    ): List<TeacherLearnerAssignment>

    fun observeLearnerMessages(
        learnerId: String,
        teacherId: String
    ): Flow<List<TeacherChatMessage>>

    suspend fun sendLearnerMessage(
        learnerId: String,
        teacherId: String,
        text: String
    )
    suspend fun createAssignment(
        teacherUid: String,
        title: String,
        subject: String,
        totalMarks: Int,
        dueDate: String
    )
}