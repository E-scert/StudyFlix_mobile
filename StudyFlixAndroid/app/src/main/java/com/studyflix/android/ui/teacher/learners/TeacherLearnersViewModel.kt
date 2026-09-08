package com.studyflix.android.ui.teacher.learners

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.studyflix.android.domain.usecase.teacher.GetTeacherLearnersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherLearnersViewModel @Inject constructor(
    private val getTeacherLearnersUseCase: GetTeacherLearnersUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            TeacherLearnersUiState()
        )

    val uiState: StateFlow<TeacherLearnersUiState> =
        _uiState.asStateFlow()

    init {
        loadLearners()
    }

    private fun loadLearners() {

        val uid =
            firebaseAuth.currentUser?.uid
                ?: return

        viewModelScope.launch {

            try {

                val learners =
                    getTeacherLearnersUseCase(uid)

                _uiState.value =
                    _uiState.value.copy(
                        learners = learners,
                        isLoading = false,
                        errorMessage = null
                    )

            } catch (e: Exception) {

                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
            }
        }
    }
}