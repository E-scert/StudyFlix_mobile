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

data class AssignmentsUiState(
    val assignments: List<Assignment> = emptyList()
)



@HiltViewModel
class AssignmentsViewModel @Inject constructor(
    private val repository: AssignmentRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(AssignmentsUiState())

    val uiState: StateFlow<AssignmentsUiState> =
        _uiState.asStateFlow()

    init {

        viewModelScope.launch {

            repository.observeAssignments()
                .collect { assignments ->

                    _uiState.value =
                        _uiState.value.copy(
                            assignments = assignments
                        )
                }
        }
    }



}

