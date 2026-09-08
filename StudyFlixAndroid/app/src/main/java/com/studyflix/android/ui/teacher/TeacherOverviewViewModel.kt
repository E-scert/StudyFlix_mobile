package com.studyflix.android.ui.teacher.overview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.studyflix.android.domain.usecase.teacher.GetTeacherOverviewUseCase
import com.studyflix.android.domain.usecase.teacher.GetTeacherProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherOverviewViewModel @Inject constructor(
    private val getTeacherProfileUseCase: GetTeacherProfileUseCase,
    private val getTeacherOverviewUseCase: GetTeacherOverviewUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel()  {

    private val _uiState =
        MutableStateFlow(
            TeacherOverviewUiState()
        )

    val uiState: StateFlow<TeacherOverviewUiState> =
        _uiState.asStateFlow()

    init {
        loadOverview()
    }

    private fun loadOverview() {

        val uid =
            firebaseAuth.currentUser?.uid
                ?: return

        viewModelScope.launch {

            try {

                val overview = getTeacherOverviewUseCase(uid)

                Log.d("TEACHER_OVERVIEW", "Learners = ${overview.totalLearners}")
                Log.d("TEACHER_OVERVIEW", "Online = ${overview.onlineLearners}")
                Log.d("TEACHER_OVERVIEW", "Assignments = ${overview.activeAssignments}")

                _uiState.value =
                    _uiState.value.copy(
                        totalLearners = overview.totalLearners,
                        onlineLearners = overview.onlineLearners,
                        activeAssignments = overview.activeAssignments,
                        isLoading = false,
                        errorMessage = null
                    )

            }catch (e: Exception) {

                Log.e(
                    "TEACHER_DEBUG",
                    "Overview error",
                    e
                )

                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
            }
        }
    }

}