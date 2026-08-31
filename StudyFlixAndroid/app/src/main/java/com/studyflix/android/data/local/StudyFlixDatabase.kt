package com.studyflix.android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.studyflix.android.data.local.dao.MarkDao
import com.studyflix.android.data.local.dao.QuizDao
import com.studyflix.android.data.local.dao.StudentDao
import com.studyflix.android.data.local.dao.VideoDao
import com.studyflix.android.data.local.entity.MarkEntity
import com.studyflix.android.data.local.entity.QuestionListConverter
import com.studyflix.android.data.local.entity.QuizEntity
import com.studyflix.android.data.local.entity.StringListConverter
import com.studyflix.android.data.local.entity.StudentEntity
import com.studyflix.android.data.local.entity.VideoEntity

/**
 * Local offline cache. This is what makes the app "offline-first": every
 * repository writes Firestore results here and the UI observes Room directly,
 * so content already seen keeps working without a network connection --
 * conceptually equivalent to the web app's `db.enablePersistence()` call.
 */
@Database(
    entities = [VideoEntity::class, QuizEntity::class, MarkEntity::class, StudentEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(QuestionListConverter::class, StringListConverter::class)
abstract class StudyFlixDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
    abstract fun quizDao(): QuizDao
    abstract fun markDao(): MarkDao
    abstract fun studentDao(): StudentDao

    companion object {
        const val DATABASE_NAME = "studyflix.db"
    }
}
