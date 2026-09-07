package com.studyflix.android.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.studyflix.android.core.util.FirestoreCollections
import com.studyflix.android.data.local.dao.StudentDao
import com.studyflix.android.data.local.entity.toDomain
import com.studyflix.android.data.local.entity.toEntity
import com.studyflix.android.domain.model.AccountStatus
import com.studyflix.android.domain.model.Student
import com.studyflix.android.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue

@Singleton
class StudentRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val studentDao: StudentDao,
    private val firebaseAuth: FirebaseAuth
) : StudentRepository {

    override fun observeStudent(uid: String): Flow<Student?> =
        studentDao.observe(uid).map { it?.toDomain() }

    override suspend fun refreshStudent(uid: String): Result<Student> = runCatching {
        val snapshot = firestore.collection(FirestoreCollections.STUDENTS).document(uid).get().await()
        val student = Student(
            uid = uid,
            email = snapshot.getString("email").orEmpty(),
            name = snapshot.getString("name").orEmpty(),
            subscription = snapshot.getString("subscription") ?: "trial",
            trialEnds = snapshot.getString("trialEnds").orEmpty(),
            grade = snapshot.getString("grade") ?: "Grade 8",
            school = snapshot.getString("school").orEmpty(),
            schoolId = snapshot.getString("schoolId").orEmpty(),
            status = AccountStatus.fromRaw(snapshot.getString("status")),
            completedQuizzes = (snapshot.get("completedQuizzes") as? List<*>)
                ?.filterIsInstance<String>().orEmpty(),
            createdAtMillis = snapshot.getTimestamp("createdAt")?.toDate()?.time
        ,
            videosUsed = snapshot.getLong("videosUsed")?.toInt() ?: 0,
            quizzesUsed = snapshot.getLong("quizzesUsed")?.toInt() ?: 0,
            papersUsed = snapshot.getLong("papersUsed")?.toInt() ?: 0,
        )
        studentDao.upsert(student.toEntity())
        student
    }

    override suspend fun updateSubscription(uid: String, plan: String): Result<Unit> = runCatching {
        firestore.collection(FirestoreCollections.STUDENTS)
            .document(uid)
            .update("subscription", plan)
            .await()
    }


    override suspend fun getStudent(
        uid: String
    ): Student? {

        return refreshStudent(uid)
            .getOrNull()
    }

    override suspend fun incrementVideosUsed() {

        val uid = firebaseAuth.currentUser?.uid ?: return

        firestore.collection(FirestoreCollections.STUDENTS)
            .document(uid)
            .update(
                "videosUsed",
                FieldValue.increment(1)
            )
            .await()
    }
    override suspend fun incrementQuizzesUsed() {

        val uid = firebaseAuth.currentUser?.uid ?: return

        firestore.collection(FirestoreCollections.STUDENTS)
            .document(uid)
            .update(
                "quizzesUsed",
                FieldValue.increment(1)
            )
            .await()
    }

    override suspend fun incrementPapersUsed() {

        val uid = firebaseAuth.currentUser?.uid ?: return

        firestore.collection(FirestoreCollections.STUDENTS)
            .document(uid)
            .update(
                "papersUsed",
                FieldValue.increment(1)
            )
            .await()
    }

}
