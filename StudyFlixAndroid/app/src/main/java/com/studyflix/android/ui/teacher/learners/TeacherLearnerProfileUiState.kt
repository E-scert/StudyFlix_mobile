package com.studyflix.android.ui.teacher.learners

import com.studyflix.android.domain.model.TeacherLearner

data class TeacherLearnerProfileUiState(

    val learner: TeacherLearner? = null,

    val isLoading: Boolean = true,

    val errorMessage: String? = null
)