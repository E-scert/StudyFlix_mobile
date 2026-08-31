package com.studyflix.android.ui.student.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.studyflix.android.domain.model.Student
import com.studyflix.android.domain.usecase.auth.SignOutUseCase
import com.studyflix.android.domain.usecase.student.GetStudentProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StudentHomeUiState(
    val student: Student? = null,
    val isRefreshing: Boolean = false
)

@HiltViewModel
class StudentHomeViewModel @Inject constructor(
    private val getStudentProfileUseCase: GetStudentProfileUseCase,
    private val signOutUseCase: SignOutUseCase,
    firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentHomeUiState())
    val uiState: StateFlow<StudentHomeUiState> = _uiState.asStateFlow()

    private val uid = firebaseAuth.currentUser?.uid

    init {
        uid?.let { studentUid ->
            viewModelScope.launch {
                getStudentProfileUseCase(studentUid).collect { student ->
                    _uiState.value = _uiState.value.copy(student = student)
                }
            }
            refresh()
        }
    }

    fun refresh() {
        val studentUid = uid ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            getStudentProfileUseCase.refresh(studentUid)
            _uiState.value = _uiState.value.copy(isRefreshing = false)
        }
    }

    fun logout() {
        viewModelScope.launch { signOutUseCase() }
    }
}
