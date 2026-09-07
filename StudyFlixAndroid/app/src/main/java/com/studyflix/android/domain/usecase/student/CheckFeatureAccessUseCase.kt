package com.studyflix.android.domain.usecase.student

import com.studyflix.android.domain.model.AccessResult
import com.studyflix.android.domain.model.Feature
import com.studyflix.android.domain.model.Student
import com.studyflix.android.domain.model.UsageStats
import com.studyflix.android.domain.model.toSubscriptionPlan
import com.studyflix.android.domain.subscription.SubscriptionManager

class CheckFeatureAccessUseCase(
    private val subscriptionManager: SubscriptionManager
) {

    operator fun invoke(
        student: Student,
        feature: Feature
    ): AccessResult {

        return subscriptionManager.canAccess(
            plan = student.subscription.toSubscriptionPlan(),

            usage = UsageStats(
                videosUsed = student.videosUsed,
                quizzesUsed = student.quizzesUsed,
                papersUsed = student.papersUsed
            ),

            feature = feature
        )
    }
}