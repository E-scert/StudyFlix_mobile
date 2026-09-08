package com.studyflix.android.ui.teacher.chat

import com.studyflix.android.domain.model.TeacherLearner

data class TeacherLearnersChatUiState(

    val learners: List<TeacherLearner> = emptyList(),

    val isLoading: Boolean = true,

    val errorMessage: String? = null
)