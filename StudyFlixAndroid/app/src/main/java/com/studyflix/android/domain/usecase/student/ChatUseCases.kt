package com.studyflix.android.domain.usecase.student

import com.studyflix.android.domain.model.ChatMessage
import com.studyflix.android.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveChatMessagesUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(studentUid: String): Flow<List<ChatMessage>> =
        chatRepository.observeMessages(studentUid)
}

class SendChatMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(studentUid: String, text: String): Result<Unit> =
        chatRepository.sendMessage(studentUid, text)
}
