package com.studyflix.android.data.repository


import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.studyflix.android.data.local.dao.TeacherDao
import com.studyflix.android.data.local.entity.toDomain
import com.studyflix.android.data.local.entity.toEntity
import com.studyflix.android.domain.model.AccountStatus
import com.studyflix.android.domain.model.Teacher
import com.studyflix.android.domain.model.TeacherLearner
import com.studyflix.android.domain.model.TeacherLearnerAssignment
import com.studyflix.android.domain.model.TeacherOverview
import com.studyflix.android.domain.repository.TeacherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeacherRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val teacherDao: TeacherDao
) : TeacherRepository {
    override fun observeTeacher(
        uid: String
    ): Flow<Teacher?> {

        return teacherDao
            .observe(uid)
            .map { it?.toDomain() }
    }

    override suspend fun getTeacher(
        uid: String
    ): Teacher? {

        return teacherDao
            .get(uid)
            ?.toDomain()
    }

    override suspend fun refreshTeacher(
        uid: String
    ): Result<Teacher> = runCatching {

        val snapshot =
            firestore
                .collection("teachers")
                .document(uid)
                .get()
                .await()



        val teacher = Teacher(

            uid = uid,

            email =
                snapshot.getString("email")
                    .orEmpty(),

            name =
                snapshot.getString("name")
                    .orEmpty(),

            phone =
                snapshot.getString("phone")
                    .orEmpty(),

            role =
                snapshot.getString("role")
                    ?: "teacher",

            schoolId =
                snapshot.getString("schoolId")
                    .orEmpty(),

            schoolName =
                snapshot.getString("schoolName")
                    .orEmpty(),

            schoolCode =
                snapshot.getString("schoolCode")
                    .orEmpty(),

            grade =
                snapshot.getString("grade")
                    .orEmpty(),

            selectedGrade =
                snapshot.getString("selectedGrade")
                    .orEmpty(),

            subject =
                snapshot.getString("subject")
                    .orEmpty(),

            selectedSubject =
                snapshot.getString("selectedSubject")
                    .orEmpty(),

            grades =
                (snapshot.get("grades") as? List<*>)
                    ?.filterIsInstance<String>()
                    .orEmpty(),

            subjects =
                (snapshot.get("subjects") as? List<*>)
                    ?.filterIsInstance<String>()
                    .orEmpty(),

            subscription =
                snapshot.getString("subscription")
                    ?: "trial",
            status =
                AccountStatus.fromRaw(
                    snapshot.getString("status")
                )

        )

        teacherDao.upsert(
            teacher.toEntity()
        )

        teacher
    }


    override suspend fun getTeacherOverview(
        teacherUid: String
    ): TeacherOverview {


        val teacher =
            getTeacher(teacherUid)

        if (teacher == null) {
            return TeacherOverview()
        }

        val learnersSnapshot =
            firestore.collection("students")
                .whereEqualTo(
                    "schoolId",
                    teacher.schoolId
                )
                .whereEqualTo(
                    "grade",
                    teacher.grade
                )
                .get()
                .await()


        val totalLearners =
            learnersSnapshot.size()



        val fiveMinutesAgo =
            System.currentTimeMillis() -
                    (5 * 60 * 1000)

        var onlineCount = 0

        learnersSnapshot.documents.forEach {

            val lastActive =
                it.getTimestamp("lastActive")
                    ?.toDate()
                    ?.time ?: 0

            if (lastActive > fiveMinutesAgo) {
                onlineCount++
            }
        }

        val assignmentsSnapshot =
            firestore.collection("assignments")
                .whereEqualTo(
                    "teacherId",
                    teacherUid
                )
                .get()
                .await()

        val activeAssignments =
            assignmentsSnapshot.documents.count { document ->

                document.getString("status") == "active"
            }

        android.util.Log.d(
            "TEACHER_DEBUG",
            "Assignments Found = $activeAssignments"
        )

        return TeacherOverview(
            totalLearners = totalLearners,
            onlineLearners = onlineCount,
            activeAssignments = activeAssignments
        )
    }

    override suspend fun getTeacherLearners(
        teacherUid: String
    ): List<TeacherLearner> {

        val teacher =
            getTeacher(teacherUid)
                ?: return emptyList()

        val learnersSnapshot =
            firestore.collection("students")
                .whereEqualTo(
                    "schoolId",
                    teacher.schoolId
                )
                .whereEqualTo(
                    "grade",
                    teacher.grade
                )
                .get()
                .await()

        val fiveMinutesAgo =
            System.currentTimeMillis() -
                    (5 * 60 * 1000)

        return learnersSnapshot.documents.map { document ->

            val lastActive =
                document.getTimestamp("lastActive")
                    ?.toDate()
                    ?.time ?: 0

            TeacherLearner(
                id = document.id,

                name =
                    document.getString("name")
                        .orEmpty(),

                email =
                    document.getString("email")
                        .orEmpty(),

                schoolId =
                    document.getString("schoolId")
                        .orEmpty(),

                grade =
                    document.getString("grade")
                        .orEmpty(),

                averageScore = 0,

                submissionsCount = 0,

                online =
                    lastActive > fiveMinutesAgo,
                phone =
                    document.getString("phone")
                        .orEmpty(),

                school =
                    document.getString("school")
                        .orEmpty(),

                plan =
                    document.getString("plan")
                        .orEmpty(),

                joinedDate =
                    document.getTimestamp("createdAt")
                        ?.toDate()
                        ?.time,
            )
        }
    }

    override suspend fun getLearnerById(
        learnerId: String
    ): TeacherLearner? {

        val document =
            firestore.collection("students")
                .document(learnerId)
                .get()
                .await()

        if (!document.exists()) {
            return null
        }

        return TeacherLearner(
            id = document.id,

            name = document.getString("name")
                .orEmpty(),

            email = document.getString("email")
                .orEmpty(),

            phone = document.getString("phone")
                .orEmpty(),

            school = document.getString("school")
                .orEmpty(),

            schoolId = document.getString("schoolId")
                .orEmpty(),

            grade = document.getString("grade")
                .orEmpty(),

            plan = document.getString("plan")
                .orEmpty(),

            joinedDate =
                document.getTimestamp("createdAt")
                    ?.toDate()
                    ?.time,

            online = false
        )
    }

    override suspend fun getLearnerAssignments(
        learnerId: String
    ): List<TeacherLearnerAssignment> {

        val assignmentsSnapshot =
            firestore.collection("assignments")
                .get()
                .await()

        val submissionsSnapshot =
            firestore.collection("submissions")
                .whereEqualTo(
                    "studentId",
                    learnerId
                )
                .get()
                .await()

        val submittedMap =
            submissionsSnapshot.documents.associateBy {
                it.getString("assignmentId")
            }

        return assignmentsSnapshot.documents.map { document ->

            val assignmentId = document.id

            val submission =
                submittedMap[assignmentId]

            val submitted =
                submission != null

            val score =
                submission
                    ?.get("score")
                    ?.toString()
                    ?: "-"

            TeacherLearnerAssignment(

                assignmentId = assignmentId,

                title =
                    document.getString("title")
                        .orEmpty(),

                dueDate =
                    document.getString("dueDate")
                        .orEmpty(),

                submitted = submitted,

                score = score
            )
        }
    }
}