package com.studyflix.android.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.studyflix.android.domain.model.Assignment
import com.studyflix.android.domain.model.AssignmentQuestion
import com.studyflix.android.domain.model.AssignmentSubmission
import com.studyflix.android.domain.repository.AssignmentRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await
import kotlin.text.get


@Singleton
class AssignmentRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : AssignmentRepository {

    override fun observeAssignments(): Flow<List<Assignment>> =
        callbackFlow {

            val listener =
                firestore.collection("assignments")
                    .addSnapshotListener { snapshot, _ ->

                        val assignments =
                            snapshot?.documents?.map { doc ->

                                Assignment(
                                    id = doc.id,
                                    title = doc.getString("title").orEmpty(),
                                    subject = doc.getString("subject").orEmpty(),
                                    teacherName = doc.getString("teacherName").orEmpty(),
                                    dueDate = doc.getString("dueDate").orEmpty(),
                                    totalMarks = (doc.getLong("totalMarks") ?: 0L).toInt(),
                                    status = doc.getString("status").orEmpty(),
                                    grade = doc.getString("grade").orEmpty(),
                                    schoolId = doc.getString("schoolId").orEmpty(),
                                    examiner = doc.getString("examiner").orEmpty(),

                                    examTime = doc.getString("examTime").orEmpty(),

                                    duration = (doc.getLong("duration") ?: 0L).toInt(),

                                    instructions = doc.get("instructions") as? List<String> ?: emptyList(),
                                    questions =
                                        (doc.get("questions") as? List<*>)
                                            ?.mapNotNull { item ->

                                                val question =
                                                    item as? Map<*, *>
                                                        ?: return@mapNotNull null

                                                AssignmentQuestion(
                                                    number =
                                                        (question["number"] as? Long)
                                                            ?.toInt()
                                                            ?: 0,

                                                    text =
                                                        question["text"] as? String
                                                            ?: ""
                                                )
                                            }
                                            ?: emptyList()



                                )
                            }.orEmpty()

                        trySend(assignments)
                    }

            awaitClose {
                listener.remove()
            }
        }
    override suspend fun getAssignmentById(
        assignmentId: String
    ): Assignment? {

        val doc =
            firestore.collection("assignments")
                .document(assignmentId)
                .get()
                .await()

        if (!doc.exists()) return null

        return Assignment(
            id = doc.id,
            title = doc.getString("title").orEmpty(),
            subject = doc.getString("subject").orEmpty(),
            teacherName = doc.getString("teacherName").orEmpty(),
            dueDate = doc.getString("dueDate").orEmpty(),
            totalMarks = (doc.getLong("totalMarks") ?: 0L).toInt(),
            status = doc.getString("status").orEmpty(),
            grade = doc.getString("grade").orEmpty(),
            schoolId = doc.getString("schoolId").orEmpty(),
            examiner = doc.getString("examiner").orEmpty(),
            examTime = doc.getString("examTime").orEmpty(),
            duration = (doc.getLong("duration") ?: 0L).toInt(),
            instructions = doc.get("instructions") as? List<String>?: emptyList(),
            questions =
                (doc.get("questions") as? List<*>)
                    ?.mapNotNull { item ->

                        val question =
                            item as? Map<*, *>
                                ?: return@mapNotNull null

                        AssignmentQuestion(
                            number =
                                (question["number"] as? Long)
                                    ?.toInt()
                                    ?: 0,

                            text =
                                question["text"] as? String
                                    ?: ""
                        )
                    }
                    ?: emptyList()

        )
    }


    override suspend fun submitAssignment(
        submission: AssignmentSubmission
    ) {

        android.util.Log.d(
            "ASSIGNMENT_SUBMIT",
            "Submitting: $submission"
        )
        val docRef = firestore
            .collection("submissions")
            .document()

        docRef.set(
            mapOf(
                "submissionId" to docRef.id,
                "assignmentId" to submission.assignmentId,
                "studentId" to submission.studentId,
                "submittedAt" to submission.submittedAt,
                "startedAt" to submission.startedAt,
                "answers" to submission.answers,
                "status" to "submitted",

                "isMarked" to submission.isMarked,
                "score" to submission.score,
                "feedback" to submission.feedback,

                "totalMarks" to submission.totalMarks,
                "percentage" to submission.percentage,

                "markedBy" to submission.markedBy,
                "markedAt" to submission.markedAt,
            )
        )
            .await()

        android.util.Log.d(
            "ASSIGNMENT_SUBMIT",
            "Saved document ID: ${docRef.id}"
        )





    }


    override suspend fun hasSubmitted(
        assignmentId: String,
        studentId: String
    ): Boolean {

        val result = firestore
            .collection("submissions")
            .whereEqualTo("assignmentId", assignmentId)
            .whereEqualTo("studentId", studentId)
            .get()
            .await()

        return !result.isEmpty
    }

    override suspend fun getSubmission(
        assignmentId: String,
        studentId: String
    ): AssignmentSubmission? {

        val result = firestore
            .collection("submissions")
            .whereEqualTo("assignmentId", assignmentId)
            .whereEqualTo("studentId", studentId)
            .limit(1)
            .get()
            .await()

        val document = result.documents.firstOrNull()
            ?: return null

        return AssignmentSubmission(
            assignmentId = document.getString("assignmentId").orEmpty(),
            studentId = document.getString("studentId").orEmpty(),
            submittedAt = document.getLong("submittedAt") ?: 0L,
            answers = document.get("answers") as? Map<String, String>
                ?: emptyMap()
        )
    }
    override suspend fun getSubmittedAssignmentIds(
        studentId: String
    ): Set<String> {

        val result = firestore
            .collection("submissions")
            .whereEqualTo("studentId", studentId)
            .get()
            .await()

        return result.documents
            .mapNotNull {
                it.getString("assignmentId")
            }
            .toSet()
    }

}

