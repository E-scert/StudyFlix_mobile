package com.studyflix.android.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.studyflix.android.core.util.FirestoreCollections
import com.studyflix.android.domain.model.ChatMessage
import com.studyflix.android.domain.model.UserRole
import com.studyflix.android.domain.repository.ChatRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Equivalent of ChatManager on web. Uses a Firestore snapshot listener
 * directly (rather than Room) since chat is inherently a live, server-driven
 * stream; Firestore's own disk cache already provides offline read support.
 */
@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ChatRepository {

    override fun observeMessages(studentUid: String): Flow<List<ChatMessage>> = callbackFlow {
        val registration = firestore.collection(FirestoreCollections.MESSAGES)
            .whereEqualTo("studentId", studentUid)
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener
                val messages = snapshot.documents.map { doc ->
                    ChatMessage(
                        id = doc.id,
                        studentId = studentUid,
                        senderId = doc.getString("senderId").orEmpty(),
                        senderRole = UserRole.fromCollectionName(
                            doc.getString("senderRole").orEmpty()
                        ) ?: UserRole.STUDENT,
                        text = doc.getString("text").orEmpty(),
                        timestampMillis = doc.getTimestamp("timestamp")?.toDate()?.time ?: 0L
                    )
                }
                trySend(messages)
            }
        awaitClose { registration.remove() }
    }

    override suspend fun sendMessage(studentUid: String, text: String): Result<Unit> = runCatching {
        firestore.collection(FirestoreCollections.MESSAGES).add(
            mapOf(
                "studentId" to studentUid,
                "senderId" to studentUid,
                "senderRole" to "students",
                "text" to text,
                "timestamp" to com.google.firebase.Timestamp.now()
            )
        ).await()
        Unit
    }
}
