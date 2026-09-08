package com.studyflix.android.ui.teacher.learners

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyflix.android.domain.usecase.teacher.GetLearnerAssignmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherLearnerAssignmentsViewModel @Inject constructor(
    private val getLearnerAssignmentsUseCase: GetLearnerAssignmentsUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            TeacherLearnerAssignmentsUiState()
        )

    val uiState: StateFlow<TeacherLearnerAssignmentsUiState> =
        _uiState.asStateFlow()

    fun loadAssignments(
        learnerId: String
    ) {

        viewModelScope.launch {

            try {

                val assignments =
                    getLearnerAssignmentsUseCase(
                        learnerId
                    )

                _uiState.value =
                    _uiState.value.copy(
                        assignments = assignments,
                        isLoading = false
                    )

            } catch (e: Exception) {

                _uiState.value =
                    _uiState.value.copy(
                        errorMessage = e.message,
                        isLoading = false
                    )
            }
        }
    }
}