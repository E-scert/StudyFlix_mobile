package com.studyflix.android.domain.usecase.student

import com.studyflix.android.core.util.Resource
import com.studyflix.android.domain.model.VideoContent
import com.studyflix.android.domain.repository.ContentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(
    private val contentRepository: ContentRepository
) {
    operator fun invoke(): Flow<Resource<List<VideoContent>>> = contentRepository.observeApprovedVideos()
}
