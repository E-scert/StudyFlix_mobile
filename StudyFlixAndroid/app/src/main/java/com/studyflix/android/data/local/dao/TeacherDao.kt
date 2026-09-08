package com.studyflix.android.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.studyflix.android.data.local.entity.TeacherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeacherDao {

    @Query(
        "SELECT * FROM teachers WHERE uid = :uid LIMIT 1"
    )
    fun observe(
        uid: String
    ): Flow<TeacherEntity?>

    @Query(
        "SELECT * FROM teachers WHERE uid = :uid LIMIT 1"
    )
    suspend fun get(
        uid: String
    ): TeacherEntity?

    @Upsert
    suspend fun upsert(
        teacher: TeacherEntity
    )

    @Query(
        "DELETE FROM teachers"
    )
    suspend fun clear()
}
