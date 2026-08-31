package com.studyflix.android.domain.repository

import com.studyflix.android.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

/** `messages` collection filtered by studentId, matching public/student/js/chat.js. */
interface ChatRepository {
    fun observeMessages(studentUid: String): Flow<List<ChatMessage>>

    suspend fun sendMessage(studentUid: String, text: String): Result<Unit>
}
