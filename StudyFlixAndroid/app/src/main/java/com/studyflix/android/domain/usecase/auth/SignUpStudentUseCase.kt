package com.studyflix.android.domain.usecase.auth

import com.studyflix.android.domain.model.Student
import com.studyflix.android.domain.repository.AuthRepository
import javax.inject.Inject

/** Wraps AuthRepository.signUpStudent with basic input validation. */
class SignUpStudentUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        firstName: String,
        surname: String,
        school: String,
        grade: String
    ): Result<Student> {
        if (password.length < 6) {
            return Result.failure(IllegalArgumentException("Password must be at least 6 characters."))
        }
        if (firstName.isBlank() || surname.isBlank()) {
            return Result.failure(IllegalArgumentException("First name and surname are required."))
        }
        return authRepository.signUpStudent(email, password, firstName, surname, school, grade)
    }
}
