package com.studyflix.android.ui.teacher.assignments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.studyflix.android.domain.usecase.teacher.GetTeacherAssignmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherAssignmentsViewModel @Inject constructor(
    private val getTeacherAssignmentsUseCase: GetTeacherAssignmentsUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            TeacherAssignmentsUiState()
        )

    val uiState: StateFlow<TeacherAssignmentsUiState> =
        _uiState.asStateFlow()

    init {
        loadAssignments()
    }

    private fun loadAssignments() {

        val teacherUid =
            firebaseAuth.currentUser?.uid
                ?: return

        viewModelScope.launch {

            try {

                val assignments =
                    getTeacherAssignmentsUseCase(
                        teacherUid
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