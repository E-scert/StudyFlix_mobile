package com.studyflix.android.domain.repository

import com.studyflix.android.domain.model.Student
import kotlinx.coroutines.flow.Flow

interface StudentRepository {

    fun observeStudent(uid: String): Flow<Student?>

    suspend fun refreshStudent(uid: String): Result<Student>

    suspend fun updateSubscription(
        uid: String,
        plan: String
    ): Result<Unit>

    suspend fun getStudent(
        uid: String
    ): Student?
}
