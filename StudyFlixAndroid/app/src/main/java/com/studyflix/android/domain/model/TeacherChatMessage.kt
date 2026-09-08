package com.studyflix.android.domain.model

data class TeacherChatMessage(

    val id: String = "",

    val text: String = "",

    val senderId: String = "",

    val senderName: String = "",

    val recipientId: String = "",

    val timestamp: Long = 0L,

    val read: Boolean = false
)