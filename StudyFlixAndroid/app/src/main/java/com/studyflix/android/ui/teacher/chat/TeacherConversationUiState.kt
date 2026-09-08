package com.studyflix.android.ui.teacher.chat

import com.studyflix.android.domain.model.TeacherChatMessage

data class TeacherConversationUiState(

    val messages: List<TeacherChatMessage> = emptyList(),

    val draft: String = "",

    val isLoading: Boolean = true
)