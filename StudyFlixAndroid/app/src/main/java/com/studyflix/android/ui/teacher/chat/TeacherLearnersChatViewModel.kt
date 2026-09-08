package com.studyflix.android.ui.teacher.chat

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
class TeacherLearnersChatViewModel @Inject constructor(
    private val getTeacherLearnersUseCase: GetTeacherLearnersUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            TeacherLearnersChatUiState()
        )

    val uiState: StateFlow<TeacherLearnersChatUiState> =
        _uiState.asStateFlow()

    init {
        loadLearners()
    }

    private fun loadLearners() {

        val teacherUid =
            firebaseAuth.currentUser?.uid
                ?: return

        viewModelScope.launch {

            try {

                val learners =
                    getTeacherLearnersUseCase(
                        teacherUid
                    )

                _uiState.value =
                    _uiState.value.copy(
                        learners = learners,
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