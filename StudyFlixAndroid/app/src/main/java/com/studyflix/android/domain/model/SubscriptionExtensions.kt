package com.studyflix.android.domain.model

fun String.toSubscriptionPlan(): SubscriptionPlan {
    return when (uppercase()) {

        "EZAME" -> SubscriptionPlan.EZAME

        "LIFE" -> SubscriptionPlan.LIFE

        else -> SubscriptionPlan.FREE
    }
}