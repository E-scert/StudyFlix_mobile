package com.studyflix.android.domain.model

sealed class AccessResult {

    data object Allowed : AccessResult()

    data class LimitReached(
        val message: String
    ) : AccessResult()

    data class UpgradeRequired(
        val message: String
    ) : AccessResult()
}