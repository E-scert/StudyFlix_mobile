package com.studyflix.android.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.studyflix.android.core.util.FirestoreCollections
import com.studyflix.android.domain.model.PastPaper
import com.studyflix.android.domain.repository.PastPaperRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PastPaperRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : PastPaperRepository {

    override fun observePastPapers(): Flow<List<PastPaper>> =
        callbackFlow {

            val listener =
                firestore.collection(FirestoreCollections.CONTENT)
                    .whereEqualTo("type", "paper")
                    .whereEqualTo("status", "live")
                    .addSnapshotListener { snapshot, _ ->

                        val papers =
                            snapshot?.documents?.map { doc ->

                                val rawUrl = doc.getString("fileUrl").orEmpty()
                                android.util.Log.d(

                                    "RAW_FIREBASE_URL",
                                    rawUrl
                                )
                                android.util.Log.d(
                                    "RAW_LENGTH",
                                    rawUrl.length.toString()
                                )
                                android.util.Log.d(
                                    "RAW_START",
                                    rawUrl.take(40)
                                )
                                val cleanUrl = Regex("""https://firebasestorage\.googleapis\.com[^"< ]+""").find(rawUrl)?.value.orEmpty()
                                android.util.Log.d("CLEAN_FIREBASE_URL", cleanUrl)

                                PastPaper(
                                    id = doc.id,
                                    title = doc.getString("title").orEmpty(),
                                    subject = doc.getString("subject").orEmpty(),
                                    year = (doc.getLong("year") ?: 0L).toInt(),
                                    term = doc.getString("term").orEmpty(),
                                    fileUrl = cleanUrl
                                )

                            }.orEmpty()

                        trySend(papers)
                    }

            awaitClose {
                listener.remove()
            }
        }
}