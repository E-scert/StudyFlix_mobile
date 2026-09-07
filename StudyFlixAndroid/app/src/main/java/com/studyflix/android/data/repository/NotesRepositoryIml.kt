package com.studyflix.android.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.studyflix.android.core.util.FirestoreCollections
import com.studyflix.android.domain.model.StudyNote
import com.studyflix.android.domain.repository.NotesRepository
import com.studyflix.android.domain.repository.StudentRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val studentRepository: StudentRepository
)  : NotesRepository {

    override suspend fun getNotes(): List<StudyNote> {

        val results = mutableListOf<StudyNote>()
        val uid = auth.currentUser?.uid ?: return emptyList()
        val student = studentRepository.getStudent(uid) ?: return emptyList()
        val studentGrade = student.grade

        // notes collection
        val notesDocs = firestore
            .collection(FirestoreCollections.NOTES)
            .get()
            .await()

        notesDocs.documents.forEach { doc ->


            if (doc.getString("grade") != studentGrade) {
                return@forEach
            }

            results.add(
                StudyNote(
                    id = doc.id,
                    title = doc.getString("title").orEmpty(),
                    subject = doc.getString("subject").orEmpty(),
                    topic = doc.getString("topic").orEmpty(),
                    description = doc.getString("description").orEmpty(),
                    content = doc.getString("content").orEmpty(),
                    authorName = doc.getString("authorName")
                        ?: doc.getString("author")
                        ?: "StudyFlix"
                )
            )

        }

        // content collection notes
        val teacherNotes = firestore
            .collection(FirestoreCollections.CONTENT)
            .whereEqualTo("type", "notes")
            .get()
            .await()

        teacherNotes.documents.forEach { doc ->

            if (doc.getString("grade") != studentGrade) {
                return@forEach
            }
            results.add(
                StudyNote(
                    id = doc.id,
                    title = doc.getString("title").orEmpty(),
                    subject = doc.getString("subject").orEmpty(),
                    topic = doc.getString("topic").orEmpty(),
                    description = doc.getString("description").orEmpty(),
                    content =
                        doc.getString("content")
                            ?: doc.getString("text")
                            ?: "",
                    authorName =
                        doc.getString("authorName")
                            ?: doc.getString("uploadedByName")
                            ?: "Teacher",
                    uploadedBy =
                        doc.getString("uploadedBy")
                            ?: doc.getString("teacherId")
                )
            )


        }

        return results
    }
}