package com.studyflix.android.ui.student.assignments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyflix.android.domain.model.Assignment
import com.studyflix.android.domain.model.AssignmentSubmission
import com.studyflix.android.domain.repository.AssignmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AssignmentDetailsUiState(
    val assignment: Assignment? = null
)

@HiltViewModel
class AssignmentDetailsViewModel @Inject constructor(
    private val repository: AssignmentRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            AssignmentDetailsUiState()
        )

    val uiState: StateFlow<AssignmentDetailsUiState> =
        _uiState.asStateFlow()

    fun loadAssignment(
        assignmentId: String
    ) {

        viewModelScope.launch {

            val assignment =
                repository.getAssignmentById(
                    assignmentId
                )

            _uiState.value =
                _uiState.value.copy(
                    assignment = assignment
                )
        }
    }


    fun submitAssignment(
        submission: AssignmentSubmission
    ) {

        viewModelScope.launch {

            repository.submitAssignment(
                submission
            )
        }
    }
}