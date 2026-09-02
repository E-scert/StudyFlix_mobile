package com.studyflix.android.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.studyflix.android.core.util.FirestoreCollections
import com.studyflix.android.domain.model.StudyNote
import com.studyflix.android.domain.repository.NotesRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : NotesRepository {

    override suspend fun getNotes(): List<StudyNote> {

        val results = mutableListOf<StudyNote>()

        // notes collection
        val notesDocs = firestore
            .collection(FirestoreCollections.NOTES)
            .get()
            .await()

        notesDocs.documents.forEach { doc ->

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