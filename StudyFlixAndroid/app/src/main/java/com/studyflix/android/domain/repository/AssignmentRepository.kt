package com.studyflix.android.domain.repository

import com.studyflix.android.domain.model.Assignment
import com.studyflix.android.domain.model.AssignmentSubmission
import kotlinx.coroutines.flow.Flow

interface AssignmentRepository {

    fun observeAssignments(): Flow<List<Assignment>>

    suspend fun getAssignmentById(
        assignmentId: String
    ): Assignment?

    suspend fun submitAssignment(
        submission: AssignmentSubmission
    )
}