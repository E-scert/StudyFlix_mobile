package com.studyflix.android.domain.repository

import com.studyflix.android.core.util.Resource
import com.studyflix.android.domain.model.Mark
import kotlinx.coroutines.flow.Flow

/** `marks` collection filtered by studentId, matching public/student/js/marks.js. */
interface MarksRepository {
    fun observeMarksForStudent(studentUid: String): Flow<Resource<List<Mark>>>
}
