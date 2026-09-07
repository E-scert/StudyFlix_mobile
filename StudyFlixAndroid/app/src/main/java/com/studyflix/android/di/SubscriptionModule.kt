package com.studyflix.android.di

import com.studyflix.android.domain.subscription.SubscriptionManager
import com.studyflix.android.domain.usecase.student.CheckFeatureAccessUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SubscriptionModule {

    @Provides
    @Singleton
    fun provideSubscriptionManager(): SubscriptionManager {
        return SubscriptionManager()
    }

    @Provides
    @Singleton
    fun provideCheckFeatureAccessUseCase(
        subscriptionManager: SubscriptionManager
    ): CheckFeatureAccessUseCase {
        return CheckFeatureAccessUseCase(
            subscriptionManager
        )
    }
}