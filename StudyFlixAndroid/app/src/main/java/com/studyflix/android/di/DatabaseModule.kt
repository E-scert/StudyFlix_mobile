package com.studyflix.android.di

import android.content.Context
import androidx.room.Room
import com.studyflix.android.data.local.StudyFlixDatabase
import com.studyflix.android.data.local.dao.MarkDao
import com.studyflix.android.data.local.dao.QuizDao
import com.studyflix.android.data.local.dao.StudentDao
import com.studyflix.android.data.local.dao.VideoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): StudyFlixDatabase =
        Room.databaseBuilder(context, StudyFlixDatabase::class.java, StudyFlixDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideVideoDao(db: StudyFlixDatabase): VideoDao = db.videoDao()

    @Provides
    fun provideQuizDao(db: StudyFlixDatabase): QuizDao = db.quizDao()

    @Provides
    fun provideMarkDao(db: StudyFlixDatabase): MarkDao = db.markDao()

    @Provides
    fun provideStudentDao(db: StudyFlixDatabase): StudentDao = db.studentDao()
}
