package com.studyflix.android.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.studyflix.android.core.util.FirestoreCollections
import com.studyflix.android.core.util.Resource
import com.studyflix.android.core.util.networkBoundResource
import com.studyflix.android.data.local.dao.MarkDao
import com.studyflix.android.data.local.entity.MarkEntity
import com.studyflix.android.data.local.entity.toDomain
import com.studyflix.android.domain.model.Mark
import com.studyflix.android.domain.repository.MarksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/** Equivalent of MarksManager.loadMarksFromFirestore() on web. */
@Singleton
class MarksRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val markDao: MarkDao
) : MarksRepository {

    override fun observeMarksForStudent(studentUid: String): Flow<Resource<List<Mark>>> =
        networkBoundResource(
            query = { markDao.observeForStudent(studentUid).map { list -> list.map { it.toDomain() } } },
            fetch = {
                firestore.collection(FirestoreCollections.MARKS)
                    .whereEqualTo("studentId", studentUid)
                    .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
                    .get()
                    .await()
                    .documents
                    .map { doc ->
                        MarkEntity(
                            id = doc.id,
                            studentId = studentUid,
                            name = doc.getString("name").orEmpty(),
                            dateIso = doc.getTimestamp("date")?.toDate()?.toInstant()?.toString().orEmpty(),
                            score = (doc.getLong("score") ?: 0L).toInt(),
                            total = (doc.getLong("total") ?: 0L).toInt(),
                            percentage = (doc.getLong("percentage") ?: 0L).toInt()
                        )
                    }
            },
            saveFetchResult = { marks ->
                markDao.clearForStudent(studentUid)
                markDao.upsertAll(marks)
            }
        )
}
