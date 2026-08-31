package com.studyflix.android.domain.usecase.auth

import com.studyflix.android.domain.model.UserRole
import com.studyflix.android.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Signs a user in and resolves their portal, exactly like the web flow:
 * auth.onAuthStateChanged -> getUserRole(uid) -> redirect to the matching
 * dashboard (role-manager.js: redirectToRoleBasedDashboard()). If no role
 * document exists, the user is signed back out, matching the web behaviour.
 */
class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<UserRole> {
        val signInResult = authRepository.signIn(email, password)
        val uid = signInResult.getOrElse { return Result.failure(it) }

        val role = authRepository.getUserRole(uid)
        return if (role != null) {
            Result.success(role)
        } else {
            authRepository.signOut()
            Result.failure(IllegalStateException("No role assigned to this account. Please contact your administrator."))
        }
    }
}
