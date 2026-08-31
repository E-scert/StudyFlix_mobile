package com.studyflix.android.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.studyflix.android.domain.model.AccountStatus
import com.studyflix.android.domain.model.Student

class StringListConverter {
    @androidx.room.TypeConverter
    fun fromList(value: List<String>): String = value.joinToString("|")

    @androidx.room.TypeConverter
    fun toList(value: String): List<String> = if (value.isBlank()) emptyList() else value.split("|")
}

@Entity(tableName = "students")
@TypeConverters(StringListConverter::class)
data class StudentEntity(
    @PrimaryKey val uid: String,
    val email: String,
    val name: String,
    val subscription: String,
    val trialEnds: String,
    val grade: String,
    val school: String,
    val schoolId: String,
    val status: String,
    val completedQuizzes: List<String>,
    val createdAtMillis: Long?
)

fun StudentEntity.toDomain() = Student(
    uid, email, name, subscription, trialEnds, grade, school, schoolId,
    AccountStatus.fromRaw(status), completedQuizzes, createdAtMillis
)

fun Student.toEntity() = StudentEntity(
    uid, email, name, subscription, trialEnds, grade, school, schoolId,
    status.name.lowercase(), completedQuizzes, createdAtMillis
)
