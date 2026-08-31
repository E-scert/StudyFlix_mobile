package com.studyflix.android.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.studyflix.android.domain.model.VideoContent

@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey val id: String,
    val title: String,
    val season: Int,
    val seasonName: String,
    val episode: Int,
    val duration: String,
    val views: Int,
    val subject: String,
    val locked: Boolean,
    val videoUrl: String,
    val thumbnailUrl: String
)

fun VideoEntity.toDomain() = VideoContent(
    id = id,
    title = title,
    season = season,
    seasonName = seasonName,
    episode = episode,
    duration = duration,
    views = views,
    subject = subject,
    locked = locked,
    videoUrl = videoUrl,
    thumbnailUrl = thumbnailUrl
)

fun VideoContent.toEntity() = VideoEntity(
    id = id,
    title = title,
    season = season,
    seasonName = seasonName,
    episode = episode,
    duration = duration,
    views = views,
    subject = subject,
    locked = locked,
    videoUrl = videoUrl,
    thumbnailUrl = thumbnailUrl
)
