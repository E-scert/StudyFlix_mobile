package com.studyflix.android.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.studyflix.android.core.util.FirestoreCollections
import com.studyflix.android.core.util.Resource
import com.studyflix.android.core.util.networkBoundResource
import com.studyflix.android.data.local.dao.QuizDao
import com.studyflix.android.data.local.entity.QuestionDto
import com.studyflix.android.data.local.entity.QuizEntity
import com.studyflix.android.data.local.entity.toDomain
import com.studyflix.android.domain.model.Quiz
import com.studyflix.android.domain.repository.QuizRepository
import com.studyflix.android.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/** Equivalent of QuizManager.loadQuizzes() on web: `quizzes` where status == "published". */
@Singleton
class QuizRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val quizDao: QuizDao,
    private val auth: FirebaseAuth,
    private val studentRepository: StudentRepository
) : QuizRepository {

    override fun observePublishedQuizzes(): Flow<Resource<List<Quiz>>> = networkBoundResource(
        query = { quizDao.observeAll().map { list -> list.map { it.toDomain() } } },
        fetch = {
            val uid = auth.currentUser?.uid
                ?: return@networkBoundResource emptyList()

            val student = studentRepository.getStudent(uid)
                ?: return@networkBoundResource emptyList()

            val studentGrade = student.grade

            firestore.collection(FirestoreCollections.QUIZZES)
                .whereEqualTo("status", "published")
                .get()
                .await()
                .documents
                .map { doc ->

                    val questions = (doc.get("questions") as? List<*>)?.mapNotNull { raw ->
                        val q = raw as? Map<*, *> ?: return@mapNotNull null

                        QuestionDto(
                            text = q["text"] as? String ?: "",
                            options = (q["options"] as? List<*>)
                                ?.filterIsInstance<String>()
                                .orEmpty(),
                            correctIndex = ((q["correct"] as? Long) ?: 0L).toInt(),
                            marks = ((q["marks"] as? Long) ?: 2L).toInt()
                        )
                    }.orEmpty()

                    QuizEntity(
                        id = doc.id,
                        title = doc.getString("title").orEmpty(),
                        description = doc.getString("description").orEmpty(),
                        subject = doc.getString("subject").orEmpty(),
                        grade = doc.getString("grade").orEmpty(),
                        questions = questions,
                        totalMarks = (doc.getLong("totalMarks") ?: 0L).toInt(),
                        timeLimitMinutes = (doc.getLong("timeLimit") ?: 15L).toInt()
                    )

                }
                .filter { quiz ->

                    quiz.grade == studentGrade

                }
        },
        saveFetchResult = { quizzes ->
            quizDao.clear()
            quizDao.upsertAll(quizzes)
        }
    )

    override suspend fun getQuiz(quizId: String): Quiz? = quizDao.getById(quizId)?.toDomain()

    override suspend fun submitQuizAttempt(
        studentUid: String,
        quiz: Quiz,
        answers: List<Int?>
    ): Result<Int> = runCatching {
        val score = quiz.questions.mapIndexed { index, question ->
            if (answers.getOrNull(index) == question.correctIndex) question.marks else 0
        }.sum()

        // Record the mark, matching the shape read by public/student/js/marks.js.
        firestore.collection(FirestoreCollections.MARKS).add(
            mapOf(
                "studentId" to studentUid,
                "name" to quiz.title,
                "date" to com.google.firebase.Timestamp.now(),
                "score" to score,
                "total" to quiz.totalMarks,
                "percentage" to if (quiz.totalMarks > 0) (score * 100 / quiz.totalMarks) else 0
            )
        ).await()

        // Mark quiz completed on the student's profile, like StudentAuth's
        // completedQuizzes array on web.
        firestore.collection(FirestoreCollections.STUDENTS).document(studentUid)
            .update("completedQuizzes", FieldValue.arrayUnion(quiz.id))
            .await()

        score
    }
}
