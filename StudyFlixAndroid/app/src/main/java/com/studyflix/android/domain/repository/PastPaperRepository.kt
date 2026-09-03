package com.studyflix.android.domain.repository

import com.studyflix.android.domain.model.PastPaper
import kotlinx.coroutines.flow.Flow

interface PastPaperRepository {

    fun observePastPapers(): Flow<List<PastPaper>>
}