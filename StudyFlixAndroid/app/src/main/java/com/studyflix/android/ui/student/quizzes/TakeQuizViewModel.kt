package com.studyflix.android.ui.student.quizzes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.studyflix.android.core.navigation.Screen
import com.studyflix.android.domain.model.Quiz
import com.studyflix.android.domain.repository.QuizRepository
import com.studyflix.android.domain.usecase.student.SubmitQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.delay

data class TakeQuizUiState(
    val quiz: Quiz? = null,
    val currentIndex: Int = 0,
    val answers: List<Int?> = emptyList(),
    val isSubmitting: Boolean = false,
    val finalScore: Int? = null,
    val errorMessage: String? = null,
    val timeRemainingSeconds: Int = 0,
    val timedOut: Boolean = false

    ) {
    val currentQuestion get() = quiz?.questions?.getOrNull(currentIndex)
    val isLastQuestion get() = quiz != null && currentIndex == quiz.questions.lastIndex
}

/** Equivalent of QuizManager's take-quiz flow (currentQuestionIndex / userAnswers) on web. */
@HiltViewModel
class TakeQuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val submitQuizUseCase: SubmitQuizUseCase,
    private val firebaseAuth: FirebaseAuth,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val quizId: String = checkNotNull(savedStateHandle[Screen.TakeQuiz.ARG_QUIZ_ID])

    private val _uiState = MutableStateFlow(TakeQuizUiState())
    val uiState: StateFlow<TakeQuizUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val quiz = quizRepository.getQuiz(quizId)
            _uiState.value = _uiState.value.copy(
                quiz = quiz,
                answers = quiz?.questions?.map { null } ?: emptyList(),
                timeRemainingSeconds =
                    (quiz?.timeLimitMinutes ?: 0) * 60
            )
            while (_uiState.value.timeRemainingSeconds > 0) {

                kotlinx.coroutines.delay(1000)

                _uiState.value = _uiState.value.copy(
                    timeRemainingSeconds =
                        _uiState.value.timeRemainingSeconds - 1
                )
            }

            if (_uiState.value.finalScore == null) {

                _uiState.value = _uiState.value.copy(
                    timedOut = true
                )

                submit()
            }

        }
    }

    fun selectAnswer(optionIndex: Int) {
        val updated = _uiState.value.answers.toMutableList()
        updated[_uiState.value.currentIndex] = optionIndex
        _uiState.value = _uiState.value.copy(answers = updated)
    }

    fun nextQuestion() {
        val state = _uiState.value
        if (state.quiz != null && state.currentIndex < state.quiz.questions.lastIndex) {
            _uiState.value = state.copy(currentIndex = state.currentIndex + 1)
        }
    }

    fun previousQuestion() {
        val state = _uiState.value
        if (state.currentIndex > 0) {
            _uiState.value = state.copy(currentIndex = state.currentIndex - 1)
        }
    }

    fun submit() {
        val uid = firebaseAuth.currentUser?.uid ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSubmitting = true, errorMessage = null)
            val result = submitQuizUseCase(uid, quizId, _uiState.value.answers)
            result.fold(
                onSuccess = { score ->
                    _uiState.value = _uiState.value.copy(isSubmitting = false, finalScore = score)
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isSubmitting = false,
                        errorMessage = error.message ?: "Could not submit quiz."
                    )
                }
            )
        }
    }
}
