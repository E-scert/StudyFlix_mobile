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
    val createdAtMillis: Long?,

    val videosUsed: Int,
    val quizzesUsed: Int,
    val papersUsed: Int
)

fun StudentEntity.toDomain() = Student(
    uid = uid,
    email = email,
    name = name,
    subscription = subscription,
    trialEnds = trialEnds,
    grade = grade,
    school = school,
    schoolId = schoolId,
    status = AccountStatus.fromRaw(status),
    completedQuizzes = completedQuizzes,
    createdAtMillis = createdAtMillis,

    videosUsed = videosUsed,
    quizzesUsed = quizzesUsed,
    papersUsed = papersUsed
)

fun Student.toEntity() = StudentEntity(
    uid = uid,
    email = email,
    name = name,
    subscription = subscription,
    trialEnds = trialEnds,
    grade = grade,
    school = school,
    schoolId = schoolId,
    status = status.name.lowercase(),
    completedQuizzes = completedQuizzes,
    createdAtMillis = createdAtMillis,

    videosUsed = videosUsed,
    quizzesUsed = quizzesUsed,
    papersUsed = papersUsed
)
