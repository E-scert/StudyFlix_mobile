package com.studyflix.android.domain.model

/**
 * Domain model for a document in the `content` Firestore collection where
 * type == "video" and status == "approved" (public/student/js/videos.js).
 */
data class VideoContent(
    val id: String = "",
    val title: String = "",
    val season: Int = 1,
    val seasonName: String = "",
    val episode: Int = 1,
    val duration: String = "",
    val views: Int = 0,
    val subject: String = "",
    val locked: Boolean = false,
    val videoUrl: String = "",
    val thumbnailUrl: String = ""
)
