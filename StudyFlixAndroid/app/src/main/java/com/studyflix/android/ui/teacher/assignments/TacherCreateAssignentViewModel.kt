package com.studyflix.android.ui.teacher.assignments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.studyflix.android.domain.usecase.teacher.CreateAssignmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherCreateAssignmentViewModel @Inject constructor(
    private val createAssignmentUseCase: CreateAssignmentUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            TeacherCreateAssignmentUiState()
        )

    val uiState: StateFlow<TeacherCreateAssignmentUiState> =
        _uiState.asStateFlow()

    fun updateTitle(
        value: String
    ) {
        _uiState.value =
            _uiState.value.copy(
                title = value
            )
    }

    fun updateSubject(
        value: String
    ) {
        _uiState.value =
            _uiState.value.copy(
                subject = value
            )
    }

    fun updateMarks(
        value: String
    ) {
        _uiState.value =
            _uiState.value.copy(
                totalMarks = value
            )
    }

    fun updateDueDate(
        value: String
    ) {
        _uiState.value =
            _uiState.value.copy(
                dueDate = value
            )
    }
    fun publishAssignment() {

        val teacherUid =
            firebaseAuth.currentUser?.uid
                ?: return

        viewModelScope.launch {

            createAssignmentUseCase(

                teacherUid = teacherUid,

                title = uiState.value.title,

                subject = uiState.value.subject,

                totalMarks =
                    uiState.value.totalMarks.toIntOrNull()
                        ?: 0,

                dueDate = uiState.value.dueDate
            )
        }
    }
}

