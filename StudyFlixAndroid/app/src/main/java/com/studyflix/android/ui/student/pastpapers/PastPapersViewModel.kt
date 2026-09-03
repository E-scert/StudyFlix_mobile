package com.studyflix.android.ui.student.pastpapers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyflix.android.domain.model.PastPaper
import com.studyflix.android.domain.repository.PastPaperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class PastPapersUiState(
    val papers: List<PastPaper> = emptyList()
)

@HiltViewModel
class PastPapersViewModel @Inject constructor(
    private val repository: PastPaperRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(PastPapersUiState())

    val uiState: StateFlow<PastPapersUiState> =
        _uiState.asStateFlow()

    init {

        viewModelScope.launch {

            repository.observePastPapers()
                .collect { papers ->

                    _uiState.value =
                        _uiState.value.copy(
                            papers = papers
                        )
                }
        }
    }
}