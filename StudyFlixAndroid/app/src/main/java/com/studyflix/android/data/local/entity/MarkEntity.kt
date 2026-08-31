package com.studyflix.android.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.studyflix.android.domain.model.Mark

@Entity(tableName = "marks")
data class MarkEntity(
    @PrimaryKey val id: String,
    val studentId: String,
    val name: String,
    val dateIso: String,
    val score: Int,
    val total: Int,
    val percentage: Int
)

fun MarkEntity.toDomain() = Mark(id, studentId, name, dateIso, score, total, percentage)
fun Mark.toEntity() = MarkEntity(id, studentId, name, dateIso, score, total, percentage)
