package com.studyflix.android.ui.teacher.chat


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.studyflix.android.domain.repository.TeacherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherConversationViewModel @Inject constructor(
    private val repository: TeacherRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            TeacherConversationUiState()
        )

    val uiState: StateFlow<TeacherConversationUiState> =
        _uiState.asStateFlow()

    fun loadMessages(
        learnerId: String
    ) {

        val teacherId =
            firebaseAuth.currentUser?.uid
                ?: return

        viewModelScope.launch {

            repository.observeLearnerMessages(
                learnerId,
                teacherId
            ).collect { messages ->

                _uiState.value =
                    _uiState.value.copy(
                        messages = messages,
                        isLoading = false
                    )
            }
        }
    }

    fun onDraftChange(
        value: String
    ) {

        _uiState.value =
            _uiState.value.copy(
                draft = value
            )
    }

    fun sendMessage(
        learnerId: String
    ) {

        val teacherId =
            firebaseAuth.currentUser?.uid
                ?: return

        val text =
            _uiState.value.draft.trim()

        if (text.isEmpty()) return

        viewModelScope.launch {

            repository.sendLearnerMessage(
                learnerId,
                teacherId,
                text
            )

            _uiState.value =
                _uiState.value.copy(
                    draft = ""
                )
        }
    }
}