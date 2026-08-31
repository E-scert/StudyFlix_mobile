package com.studyflix.android.ui.student.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.studyflix.android.domain.model.ChatMessage
import com.studyflix.android.domain.usecase.student.ObserveChatMessagesUseCase
import com.studyflix.android.domain.usecase.student.SendChatMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val draft: String = "",
    val isSending: Boolean = false
)

/** Equivalent of ChatManager on web: live message list + send box. */
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val observeChatMessagesUseCase: ObserveChatMessagesUseCase,
    private val sendChatMessageUseCase: SendChatMessageUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val uid = firebaseAuth.currentUser?.uid

    init {
        uid?.let { studentUid ->
            viewModelScope.launch {
                observeChatMessagesUseCase(studentUid).collect { messages ->
                    _uiState.value = _uiState.value.copy(messages = messages)
                }
            }
        }
    }

    fun onDraftChange(value: String) {
        _uiState.value = _uiState.value.copy(draft = value)
    }

    fun sendMessage() {
        val studentUid = uid ?: return
        val text = _uiState.value.draft.trim()
        if (text.isEmpty()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSending = true)
            sendChatMessageUseCase(studentUid, text)
            _uiState.value = _uiState.value.copy(isSending = false, draft = "")
        }
    }
}
