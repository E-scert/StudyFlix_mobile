package com.studyflix.android.data.local.entity

import com.studyflix.android.domain.model.Teacher
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.studyflix.android.domain.model.AccountStatus


@Entity(tableName = "teachers")
@TypeConverters(StringListConverter::class)
data class TeacherEntity(

    @PrimaryKey
    val uid: String,

    val email: String,

    val name: String,

    val phone: String,

    val role: String,

    val schoolId: String,
    val schoolName: String,
    val schoolCode: String,

    val grade: String,
    val selectedGrade: String,

    val subject: String,
    val selectedSubject: String,

    val grades: List<String>,
    val subjects: List<String>,

    val subscription: String,

    val status: String
)

fun TeacherEntity.toDomain() = Teacher(

    uid = uid,

    email = email,

    name = name,

    phone = phone,

    role = role,

    schoolId = schoolId,
    schoolName = schoolName,
    schoolCode = schoolCode,

    grade = grade,
    selectedGrade = selectedGrade,

    subject = subject,
    selectedSubject = selectedSubject,

    grades = grades,
    subjects = subjects,

    subscription = subscription,

    status = AccountStatus.fromRaw(status)
)

fun Teacher.toEntity() = TeacherEntity(

    uid = uid,

    email = email,

    name = name,

    phone = phone,

    role = role,

    schoolId = schoolId,
    schoolName = schoolName,
    schoolCode = schoolCode,

    grade = grade,
    selectedGrade = selectedGrade,

    subject = subject,
    selectedSubject = selectedSubject,

    grades = grades,
    subjects = subjects,

    subscription = subscription,

    status = status.name.lowercase()
)