package com.studyflix.android.di

import com.studyflix.android.data.repository.AuthRepositoryImpl
import com.studyflix.android.data.repository.ChatRepositoryImpl
import com.studyflix.android.data.repository.ContentRepositoryImpl
import com.studyflix.android.data.repository.MarksRepositoryImpl
import com.studyflix.android.data.repository.NotesRepositoryImpl
import com.studyflix.android.data.repository.QuizRepositoryImpl
import com.studyflix.android.data.repository.StudentRepositoryImpl
import com.studyflix.android.domain.repository.AuthRepository
import com.studyflix.android.domain.repository.ChatRepository
import com.studyflix.android.domain.repository.ContentRepository
import com.studyflix.android.domain.repository.MarksRepository
import com.studyflix.android.domain.repository.NotesRepository
import com.studyflix.android.domain.repository.QuizRepository
import com.studyflix.android.domain.repository.StudentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Binds each interface in `domain.repository` to its concrete `data.repository`
 * implementation. Keeping this separate from FirebaseModule/DatabaseModule
 * means the domain layer never needs to know these are Firebase + Room backed.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindStudentRepository(impl: StudentRepositoryImpl): StudentRepository

    @Binds
    @Singleton
    abstract fun bindContentRepository(impl: ContentRepositoryImpl): ContentRepository

    @Binds
    @Singleton
    abstract fun bindQuizRepository(impl: QuizRepositoryImpl): QuizRepository

    @Binds
    @Singleton
    abstract fun bindMarksRepository(impl: MarksRepositoryImpl): MarksRepository

    @Binds
    @Singleton
    abstract fun bindChatRepository(impl: ChatRepositoryImpl): ChatRepository

    @Binds
    abstract fun bindNotesRepository(
        impl: NotesRepositoryImpl
    ): NotesRepository
}
