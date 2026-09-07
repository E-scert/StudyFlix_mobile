package com.studyflix.android.ui.student.videos

import androidx.lifecycle.ViewModel
import com.studyflix.android.domain.model.AccessResult
import com.studyflix.android.domain.model.Feature
import com.studyflix.android.domain.usecase.student.CheckFeatureAccessUseCase
import com.studyflix.android.domain.usecase.student.GetCurrentStudentUseCase
import com.studyflix.android.domain.usecase.student.IncrementPaperUsageUseCase
import com.studyflix.android.domain.usecase.student.IncrementQuizUsageUseCase
import com.studyflix.android.domain.usecase.student.IncrementVideoUsageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val getCurrentStudentUseCase: GetCurrentStudentUseCase,
    private val checkFeatureAccessUseCase: CheckFeatureAccessUseCase,
    private val incrementVideoUsageUseCase: IncrementVideoUsageUseCase,
    private val incrementPaperUsageUseCase: IncrementPaperUsageUseCase,
    private val incrementQuizUsageUseCase: IncrementQuizUsageUseCase
) : ViewModel() {

    suspend fun checkAccess(): AccessResult {

        val student =
            getCurrentStudentUseCase()
                ?: return AccessResult.UpgradeRequired(
                    "Student account could not be loaded."
                )

        return checkFeatureAccessUseCase(
            student,
            Feature.VIDEOS
        )
    }

    suspend fun recordUsage() {
        incrementVideoUsageUseCase()
    }

    suspend fun checkPastPaperAccess(): AccessResult {

        val student =
            getCurrentStudentUseCase()
                ?: return AccessResult.UpgradeRequired(
                    "Student account could not be loaded."
                )

        return checkFeatureAccessUseCase(
            student,
            Feature.PAST_PAPERS
        )
    }

    suspend fun recordPaperUsage() {
        incrementPaperUsageUseCase()
    }

    suspend fun checkQuizAccess(): AccessResult {

        val student =
            getCurrentStudentUseCase()
                ?: return AccessResult.UpgradeRequired(
                    "Student account could not be loaded."
                )

        return checkFeatureAccessUseCase(
            student,
            Feature.QUIZZES
        )
    }

    suspend fun recordQuizUsage() {
        incrementQuizUsageUseCase()
    }

    suspend fun checkTeacherChatAccess(): AccessResult {

        val student =
            getCurrentStudentUseCase()
                ?: return AccessResult.UpgradeRequired(
                    "Student account could not be loaded."
                )

        return checkFeatureAccessUseCase(
            student,
            Feature.TEACHER_CHAT
        )
    }

}