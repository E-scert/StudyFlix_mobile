package com.studyflix.android.domain.model

data class PastPaper(
    val id: String,
    val title: String,
    val subject: String,
    val year: Int,
    val term: String,
    val fileUrl: String
)