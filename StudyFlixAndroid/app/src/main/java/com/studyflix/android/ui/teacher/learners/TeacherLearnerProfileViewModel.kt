package com.studyflix.android.ui.teacher.learners

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyflix.android.domain.repository.TeacherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherLearnerProfileViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            TeacherLearnerProfileUiState()
        )

    val uiState: StateFlow<TeacherLearnerProfileUiState> =
        _uiState.asStateFlow()

    fun loadLearner(
        learnerId: String
    ) {

        viewModelScope.launch {

            try {

                val learner =
                    teacherRepository.getLearnerById(
                        learnerId
                    )

                _uiState.value =
                    _uiState.value.copy(
                        learner = learner,
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