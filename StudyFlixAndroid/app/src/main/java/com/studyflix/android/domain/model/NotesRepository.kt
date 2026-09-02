package com.studyflix.android.domain.repository

import com.studyflix.android.domain.model.StudyNote

interface NotesRepository {

    suspend fun getNotes(): List<StudyNote>
}