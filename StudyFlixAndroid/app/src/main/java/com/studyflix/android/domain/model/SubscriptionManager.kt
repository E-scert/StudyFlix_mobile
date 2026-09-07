package com.studyflix.android.domain.subscription

import com.studyflix.android.domain.model.*

class SubscriptionManager {

    fun canAccess(
        plan: SubscriptionPlan,
        usage: UsageStats,
        feature: Feature
    ): AccessResult {

        return when (plan) {

            SubscriptionPlan.LIFE -> {
                AccessResult.Allowed
            }

            SubscriptionPlan.EZAME -> {

                when (feature) {

                    Feature.TEACHER_CHAT -> {
                        AccessResult.UpgradeRequired(
                            "Teacher Chat requires Life Plan"
                        )
                    }

                    else -> {
                        AccessResult.Allowed
                    }
                }
            }

            SubscriptionPlan.FREE -> {

                when (feature) {

                    Feature.VIDEOS -> {
                        if (usage.videosUsed >= 5)
                            AccessResult.LimitReached(
                                "Video limit reached"
                            )
                        else
                            AccessResult.Allowed
                    }

                    Feature.QUIZZES -> {
                        if (usage.quizzesUsed >= 5)
                            AccessResult.LimitReached(
                                "Quiz limit reached"
                            )
                        else
                            AccessResult.Allowed
                    }

                    Feature.PAST_PAPERS -> {
                        if (usage.papersUsed >= 5)
                            AccessResult.LimitReached(
                                "Past paper limit reached"
                            )
                        else
                            AccessResult.Allowed
                    }

                    Feature.TEACHER_CHAT -> {
                        AccessResult.UpgradeRequired(
                            "Teacher Chat requires Ezame or Life"
                        )
                    }

                    Feature.MENTOR_CHAT -> {
                        AccessResult.UpgradeRequired(
                            "Mentor Chat requires Ezame or Life"
                        )
                    }
                }
            }
        }
    }
}