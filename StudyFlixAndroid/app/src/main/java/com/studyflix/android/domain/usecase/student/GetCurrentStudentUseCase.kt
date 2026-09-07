package com.studyflix.android.domain.usecase.student

import com.google.firebase.auth.FirebaseAuth
import com.studyflix.android.domain.model.Student
import com.studyflix.android.domain.repository.StudentRepository
import javax.inject.Inject

class GetCurrentStudentUseCase @Inject constructor(
    private val repository: StudentRepository,
    private val firebaseAuth: FirebaseAuth
) {

    suspend operator fun invoke(): Student? {

        val uid = firebaseAuth.currentUser?.uid
            ?: return null

        return repository.getStudent(uid)
    }
}