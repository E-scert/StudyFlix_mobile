package com.studyflix.android.domain.model

/**
 * Domain model for a document in the `content` Firestore collection where
 * type == "video" and status == "approved" (public/student/js/videos.js).
 */
data class VideoContent(
    val id: String,
    val title: String,
    val season: Int,
    val seasonName: String,
    val episode: Int,
    val duration: String,
    val views: Int,
    val subject: String,
    val grade: String,
    val locked: Boolean,
    val videoUrl: String,
    val thumbnailUrl: String
)
