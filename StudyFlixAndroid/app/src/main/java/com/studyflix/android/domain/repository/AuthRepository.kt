package com.studyflix.android.domain.repository

import com.studyflix.android.domain.model.Student
import com.studyflix.android.domain.model.UserRole
import kotlinx.coroutines.flow.Flow

/**
 * Contract for authentication + role resolution, matching the behaviour of
 * public/shared/role-manager.js and the three portal-specific auth.js files.
 */
interface AuthRepository {

    /** Emits the signed-in Firebase uid, or null when signed out. Backed by addAuthStateListener. */
    fun observeAuthState(): Flow<String?>

    val currentUid: String?

    suspend fun signIn(email: String, password: String): Result<String>

    /**
     * Student self-service sign-up. Creates the Firebase Auth account AND the
     * matching `students/{uid}` Firestore document, exactly like
     * StudentAuth.loadUserData()'s "create new user document" branch on web.
     */
    suspend fun signUpStudent(
        email: String,
        password: String,
        firstName: String,
        surname: String,
        school: String,
        grade: String
    ): Result<Student>

    /**
     * Resolves which portal a uid belongs to by probing admins -> teachers -> students,
     * same order/logic as getUserRole() in role-manager.js.
     */
    suspend fun getUserRole(uid: String): UserRole?

    suspend fun signOut()

    suspend fun sendPasswordReset(email: String): Result<Unit>
}
