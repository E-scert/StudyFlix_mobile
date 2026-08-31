package com.studyflix.android.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.studyflix.android.domain.model.Quiz
import com.studyflix.android.domain.model.QuizQuestion
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

@Serializable
data class QuestionDto(
    val text: String,
    val options: List<String>,
    val correctIndex: Int,
    val marks: Int
)

class QuestionListConverter {
    @TypeConverter
    fun fromList(value: List<QuestionDto>): String = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String): List<QuestionDto> =
        Json.decodeFromString(value)
}

@Entity(tableName = "quizzes")
@TypeConverters(QuestionListConverter::class)
data class QuizEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val subject: String,
    val grade: String,
    val questions: List<QuestionDto>,
    val totalMarks: Int,
    val timeLimitMinutes: Int
)

fun QuizEntity.toDomain() = Quiz(
    id = id,
    title = title,
    description = description,
    subject = subject,
    grade = grade,
    questions = questions.map { QuizQuestion(it.text, it.options, it.correctIndex, it.marks) },
    totalMarks = totalMarks,
    timeLimitMinutes = timeLimitMinutes
)

fun Quiz.toEntity() = QuizEntity(
    id = id,
    title = title,
    description = description,
    subject = subject,
    grade = grade,
    questions = questions.map { QuestionDto(it.text, it.options, it.correctIndex, it.marks) },
    totalMarks = totalMarks,
    timeLimitMinutes = timeLimitMinutes
)
