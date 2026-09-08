package com.studyflix.android.ui.teacher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.studyflix.android.domain.usecase.teacher.GetTeacherProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherDashboardViewModel @Inject constructor(
    private val getTeacherProfileUseCase: GetTeacherProfileUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            TeacherDashboardUiState()
        )

    val uiState: StateFlow<TeacherDashboardUiState> =
        _uiState.asStateFlow()

    init {
        loadTeacher()
    }

    private fun loadTeacher() {

        val uid =
            firebaseAuth.currentUser?.uid
                ?: return

        viewModelScope.launch {

            getTeacherProfileUseCase.refresh(uid)

            getTeacherProfileUseCase(uid)
                .collect { teacher ->

                    _uiState.value =
                        _uiState.value.copy(
                            teacher = teacher,
                            isLoading = false,
                            errorMessage = null
                        )
                }
        }
    }
}