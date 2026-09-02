package com.studyflix.android.ui.student.notes

import com.studyflix.android.domain.model.StudyNote

data class NotesUiState(

    val notes: List<StudyNote> = emptyList(),

    val searchQuery: String = "",

    val selectedSubject: String = "all",

    val selectedSection: String = "all",

    val isLoading: Boolean = false
)