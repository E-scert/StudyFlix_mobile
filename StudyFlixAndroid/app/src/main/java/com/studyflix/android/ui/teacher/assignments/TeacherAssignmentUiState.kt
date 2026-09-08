package com.studyflix.android.ui.teacher.assignments

import com.studyflix.android.domain.model.TeacherAssignment

data class TeacherAssignmentsUiState(

    val assignments: List<TeacherAssignment> = emptyList(),

    val isLoading: Boolean = true,

    val errorMessage: String? = null
)