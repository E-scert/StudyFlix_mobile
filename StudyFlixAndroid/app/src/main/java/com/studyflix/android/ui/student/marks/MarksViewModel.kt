package com.studyflix.android.ui.student.marks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.studyflix.android.core.util.Resource
import com.studyflix.android.domain.model.Mark
import com.studyflix.android.domain.usecase.student.GetMarksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MarksUiState(
    val marks: List<Mark> = emptyList(),
    val averagePercentage: Int = 0,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

/** Equivalent of MarksManager on web: per-student marks list + summary average. */
@HiltViewModel
class MarksViewModel @Inject constructor(
    private val getMarksUseCase: GetMarksUseCase,
    firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(MarksUiState())
    val uiState: StateFlow<MarksUiState> = _uiState.asStateFlow()

    init {
        val uid = firebaseAuth.currentUser?.uid
        if (uid != null) {
            viewModelScope.launch {
                getMarksUseCase(uid).collect { resource ->
                    _uiState.value = when (resource) {
                        is Resource.Loading -> _uiState.value.copy(isLoading = true)
                        is Resource.Success -> _uiState.value.copy(
                            marks = resource.data,
                            averagePercentage = resource.data.map { it.percentage }.average().let {
                                if (it.isNaN()) 0 else it.toInt()
                            },
                            isLoading = false,
                            errorMessage = null
                        )
                        is Resource.Error -> _uiState.value.copy(isLoading = false, errorMessage = resource.message)
                    }
                }
            }
        }
    }
}
