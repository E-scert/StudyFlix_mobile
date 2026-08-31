package com.studyflix.android.domain.repository

import com.studyflix.android.core.util.Resource
import com.studyflix.android.domain.model.VideoContent
import kotlinx.coroutines.flow.Flow

/**
 * Videos ("content" collection where type == "video" && status == "approved"),
 * matching public/student/js/videos.js loadVideos().
 */
interface ContentRepository {
    fun observeApprovedVideos(): Flow<Resource<List<VideoContent>>>
}
