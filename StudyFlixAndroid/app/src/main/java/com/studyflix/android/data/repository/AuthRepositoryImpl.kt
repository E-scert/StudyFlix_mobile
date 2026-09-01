package com.studyflix.android.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.studyflix.android.core.util.FirestoreCollections
import com.studyflix.android.domain.model.AccountStatus
import com.studyflix.android.domain.model.Student
import com.studyflix.android.domain.model.UserRole
import com.studyflix.android.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Talks directly to Firebase Auth + Firestore. Role resolution intentionally
 * probes admins -> teachers -> students in that order, mirroring
 * getUserRole() in public/shared/role-manager.js, so behaviour stays
 * consistent between the web and Android clients.
 */
@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : AuthRepository {

    override val currentUid: String?
        get() = auth.currentUser?.uid

    override fun observeAuthState(): Flow<String?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser?.uid)
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }.distinctUntilChanged()

    override suspend fun signIn(email: String, password: String): Result<String> = runCatching {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        result.user?.uid ?: error("Sign-in succeeded but no user was returned.")
    }

    override suspend fun signUpStudent(
        email: String,
        password: String,
        firstName: String,
        surname: String,
        school: String,
        grade: String
    ): Result<Student> = runCatching {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val uid = authResult.user?.uid ?: error("Sign-up succeeded but no user was returned.")

        // Mirrors the default document shape created in StudentAuth.loadUserData()
        // on web when no `students/{uid}` document exists yet.
        val trialEnds = Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000))
        val student = Student(
            uid = uid,
            email = email,
            name = "$firstName $surname".trim(),
            subscription = "trial",
            trialEnds = trialEnds.toInstant().toString(),
            grade = grade,
            school = school,
            schoolId = "",
            status = AccountStatus.PENDING,
            completedQuizzes = emptyList()
        )

        firestore.collection(FirestoreCollections.STUDENTS)
            .document(uid)
            .set(studentToMap(student), SetOptions.merge())
            .await()

        student
    }

    override suspend fun getUserRole(uid: String): UserRole? {
        for (role in listOf(UserRole.ADMIN, UserRole.TEACHER, UserRole.STUDENT)) {
            val doc = firestore.collection(role.collectionName).document(uid).get().await()
            if (doc.exists()) return role
        }
        return null
    }

    override suspend fun signOut() {
        // Firebase Auth sign-out is global by design (see role-manager.js
        // portalScopedLogout() comment on web) -- there is no per-portal
        // session on the SDK level, only per-portal navigation afterward.
        auth.signOut()
    }

    override suspend fun sendPasswordReset(email: String): Result<Unit> = runCatching {
        auth.sendPasswordResetEmail(email).await()
    }

    private fun studentToMap(student: Student): Map<String, Any?> = mapOf(
        "uid" to student.uid,
        "email" to student.email,
        "name" to student.name,
        "subscription" to student.subscription,
        "trialEnds" to student.trialEnds,
        "grade" to student.grade,
        "school" to student.school,
        "schoolId" to student.schoolId,
        "status" to "pending",
        "completedQuizzes" to student.completedQuizzes,
        "marks" to emptyList<Any>(),
        "createdAt" to com.google.firebase.firestore.FieldValue.serverTimestamp()
    )
}
