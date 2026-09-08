package com.studyflix.android.ui.teacher.learners

import com.studyflix.android.domain.model.TeacherLearner

data class TeacherLearnersUiState(

    val learners: List<TeacherLearner> = emptyList(),

    val isLoading: Boolean = true,

    val errorMessage: String? = null
)