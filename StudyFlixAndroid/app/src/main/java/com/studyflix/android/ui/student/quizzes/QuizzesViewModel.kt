package com.studyflix.android.ui.student.quizzes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyflix.android.core.util.Resource
import com.studyflix.android.domain.model.Quiz
import com.studyflix.android.domain.usecase.student.GetQuizzesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuizzesUiState(
    val quizzes: List<Quiz> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

@HiltViewModel
class QuizzesViewModel @Inject constructor(
    private val getQuizzesUseCase: GetQuizzesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizzesUiState())
    val uiState: StateFlow<QuizzesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getQuizzesUseCase().collect { resource ->
                _uiState.value = when (resource) {
                    is Resource.Loading -> _uiState.value.copy(isLoading = true)
                    is Resource.Success -> _uiState.value.copy(quizzes = resource.data, isLoading = false, errorMessage = null)
                    is Resource.Error -> _uiState.value.copy(isLoading = false, errorMessage = resource.message)
                }
            }
        }
    }
}
