package com.studyflix.android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.studyflix.android.data.local.entity.MarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkDao {
    @Query("SELECT * FROM marks WHERE studentId = :studentId ORDER BY dateIso DESC")
    fun observeForStudent(studentId: String): Flow<List<MarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(marks: List<MarkEntity>)

    @Query("DELETE FROM marks WHERE studentId = :studentId")
    suspend fun clearForStudent(studentId: String)
}
