package com.studyflix.android.ui.teacher.learners

import com.studyflix.android.domain.model.TeacherLearnerAssignment

data class TeacherLearnerAssignmentsUiState(

    val assignments: List<TeacherLearnerAssignment> = emptyList(),

    val isLoading: Boolean = true,

    val errorMessage: String? = null
)