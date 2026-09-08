package com.studyflix.android.ui.teacher.learners

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.studyflix.android.domain.usecase.teacher.GetLearnerAssignmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherLearnerAssignmentsViewModel @Inject constructor(
    private val getLearnerAssignmentsUseCase: GetLearnerAssignmentsUseCase,
    private val firebaseAuth: FirebaseAuth
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

                val teacherUid =
                    firebaseAuth.currentUser?.uid
                        ?: return@launch

                val assignments =
                    getLearnerAssignmentsUseCase(
                        teacherUid,
                        learnerId
                    )

                android.util.Log.d(
                    "LEARNER_ASSIGNMENTS",
                    "Loaded ${assignments.size} assignments"
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