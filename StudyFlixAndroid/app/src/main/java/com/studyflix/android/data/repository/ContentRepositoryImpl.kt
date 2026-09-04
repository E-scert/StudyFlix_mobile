package com.studyflix.android.data.repository


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.studyflix.android.core.util.FirestoreCollections
import com.studyflix.android.core.util.Resource
import com.studyflix.android.core.util.networkBoundResource
import com.studyflix.android.data.local.dao.VideoDao
import com.studyflix.android.data.local.entity.VideoEntity
import com.studyflix.android.data.local.entity.toDomain
import com.studyflix.android.domain.model.VideoContent
import com.studyflix.android.domain.repository.ContentRepository
import com.studyflix.android.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Equivalent of VideoManager.loadVideos() on web: queries `content` where
 * type == "video" && status == "approved", ordered by createdAt desc.
 */
@Singleton
class ContentRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val videoDao: VideoDao,
    private val auth: FirebaseAuth,
    private val studentRepository: StudentRepository
) : ContentRepository {

    override fun observeApprovedVideos(): Flow<Resource<List<VideoContent>>> = networkBoundResource(
        query = { videoDao.observeAll().map { list -> list.map(VideoEntity::toDomain) } },
        fetch = {

            val uid = auth.currentUser?.uid
                ?: return@networkBoundResource emptyList()

            val student = studentRepository.getStudent(uid)
                ?: return@networkBoundResource emptyList()

            val studentGrade = student.grade

            firestore.collection(FirestoreCollections.CONTENT)
                .whereEqualTo("type", "video")
                .whereEqualTo("status", "live")
                .get()
                .await()
                .also { snapshot ->



                    snapshot.documents.forEach { doc ->

                        android.util.Log.d(
                            "VIDEOS",
                            "Doc=${doc.id}, title=${doc.getString("title")}"
                        )
                    }
                }

                .documents
                .map { doc ->
                    VideoEntity(
                        id = doc.id,
                        title = doc.getString("title").orEmpty(),
                        season = (doc.getLong("season") ?: 1L).toInt(),
                        seasonName = doc.getString("seasonName").orEmpty(),
                        episode = (doc.getLong("episode") ?: 1L).toInt(),
                        duration = doc.getString("duration").orEmpty(),
                        views = (doc.getLong("views") ?: 0L).toInt(),
                        subject = doc.getString("subject").orEmpty(),
                        grade = doc.getString("grade").orEmpty(),
                        locked = doc.getBoolean("locked") ?: false,
                        videoUrl =
                            doc.getString("videoUrl")
                                ?: doc.getString("fileUrl")
                                ?: "",
                        thumbnailUrl = doc.getString("thumbnailUrl").orEmpty()
                    )
                }.filter {
                    video -> video.grade == studentGrade
            }
        },
        saveFetchResult = { videos ->
            videoDao.clear()
            videoDao.upsertAll(videos)
        }
    )
}
