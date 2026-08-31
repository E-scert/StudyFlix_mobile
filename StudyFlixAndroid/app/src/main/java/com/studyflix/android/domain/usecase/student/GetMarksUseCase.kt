package com.studyflix.android.domain.usecase.student

import com.studyflix.android.core.util.Resource
import com.studyflix.android.domain.model.Mark
import com.studyflix.android.domain.repository.MarksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMarksUseCase @Inject constructor(
    private val marksRepository: MarksRepository
) {
    operator fun invoke(studentUid: String): Flow<Resource<List<Mark>>> =
        marksRepository.observeMarksForStudent(studentUid)
}
