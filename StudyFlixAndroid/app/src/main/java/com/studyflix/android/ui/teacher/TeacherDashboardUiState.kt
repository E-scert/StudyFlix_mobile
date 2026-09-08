package com.studyflix.android.ui.teacher

import com.studyflix.android.domain.model.Teacher

data class TeacherDashboardUiState(

    val teacher: Teacher? = null,

    val isLoading: Boolean = true,

    val errorMessage: String? = null
)