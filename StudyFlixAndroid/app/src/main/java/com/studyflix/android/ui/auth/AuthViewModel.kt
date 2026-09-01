package com.studyflix.android.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyflix.android.domain.model.Student
import com.studyflix.android.domain.model.UserRole
import com.studyflix.android.domain.usecase.auth.SignInUseCase
import com.studyflix.android.domain.usecase.auth.SignUpStudentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/** UI state shared by the login screen. */
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val signedInRole: UserRole? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(email = value, errorMessage = null)
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(password = value, errorMessage = null)
    }

    fun signIn() {
        val state = _uiState.value
        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(errorMessage = "Please enter both email and password.")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = signInUseCase(state.email.trim(), state.password)
            result.fold(
                onSuccess = { role ->
                    _uiState.value = _uiState.value.copy(isLoading = false, signedInRole = role)
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Sign-in failed. Please try again."
                    )
                }
            )
        }
    }

    fun consumeSignedInEvent() {
        _uiState.value = _uiState.value.copy(signedInRole = null)
    }
    fun showPortalError(message: String) {
        _uiState.value = _uiState.value.copy(
            errorMessage = message
        )
    }
}

/** UI state for the student self-service sign-up screen. */
data class SignUpUiState(
    val firstName: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val school: String = "",
    val grade: String = "Grade 8",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val signedUpStudent: Student? = null
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpStudentUseCase: SignUpStudentUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun onFieldChange(update: SignUpUiState.() -> SignUpUiState) {
        _uiState.value = _uiState.value.update().copy(errorMessage = null)
    }

    fun signUp() {
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, errorMessage = null)
            val result = signUpStudentUseCase(
                email = state.email.trim(),
                password = state.password,
                firstName = state.firstName.trim(),
                surname = state.surname.trim(),
                school = state.school,
                grade = state.grade
            )
            result.fold(
                onSuccess = { student ->
                    _uiState.value = _uiState.value.copy(isLoading = false, signedUpStudent = student)
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Sign-up failed. Please try again."
                    )
                }
            )
        }
    }
}
