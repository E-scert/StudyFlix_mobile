package com.studyflix.android.domain.model

/** Domain model for a document in the `messages` Firestore collection, filtered by studentId. */
data class ChatMessage(
    val id: String = "",
    val studentId: String = "",
    val senderId: String = "",
    val senderRole: UserRole = UserRole.STUDENT,
    val text: String = "",
    val timestampMillis: Long = 0L
)
