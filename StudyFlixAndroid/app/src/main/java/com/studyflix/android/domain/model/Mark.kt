package com.studyflix.android.domain.model

/** Domain model for a document in the `marks` Firestore collection, filtered by studentId. */
data class Mark(
    val id: String = "",
    val studentId: String = "",
    val name: String = "",
    val dateIso: String = "",
    val score: Int = 0,
    val total: Int = 0,
    val percentage: Int = 0
)
