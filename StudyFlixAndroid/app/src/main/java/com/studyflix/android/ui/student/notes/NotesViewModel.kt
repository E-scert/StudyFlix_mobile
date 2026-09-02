package com.studyflix.android.ui.student.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyflix.android.domain.model.StudyNote
import com.studyflix.android.domain.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        NotesUiState()
    )

    val uiState: StateFlow<NotesUiState> =
        _uiState.asStateFlow()

    init {

        viewModelScope.launch {

            val notes: List<StudyNote> =
                repository.getNotes()

            _uiState.value =
                _uiState.value.copy(
                    notes = notes
                )
        }
    }

    fun onSearchQueryChange(query: String) {

        _uiState.value =
            _uiState.value.copy(
                searchQuery = query
            )
    }

    fun selectSubject(subject: String) {

        _uiState.value =
            _uiState.value.copy(
                selectedSubject = subject
            )
    }

    fun selectSection(section: String) {

        _uiState.value =
            _uiState.value.copy(
                selectedSection = section
            )
    }

    fun getNoteById(noteId: String): StudyNote? {

        return _uiState.value.notes.find {
            it.id == noteId
        }
    }
}