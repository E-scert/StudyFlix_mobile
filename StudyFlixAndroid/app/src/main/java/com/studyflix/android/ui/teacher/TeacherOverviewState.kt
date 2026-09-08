package com.studyflix.android.ui.teacher.overview

data class TeacherOverviewUiState(

    val totalLearners: Int = 0,

    val onlineLearners: Int = 0,

    val activeAssignments: Int = 0,

    val isLoading: Boolean = true,

    val errorMessage: String? = null
)