package com.studyflix.android.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.core.stringPreferencesKey
private val Context.dataStore by preferencesDataStore(
    name = "assignment_timer"
)

class AssignmentTimerDataStore(
    private val context: Context
) {

    suspend fun saveEndTime(
        assignmentId: String,
        endTime: Long
    ) {
        context.dataStore.edit { prefs ->
            prefs[
                longPreferencesKey(assignmentId)
            ] = endTime
        }
    }

    suspend fun getEndTime(
        assignmentId: String
    ): Long? {

        val prefs =
            context.dataStore.data.first()

        return prefs[
            longPreferencesKey(assignmentId)
        ]
    }

    suspend fun clearEndTime(
        assignmentId: String
    ) {
        context.dataStore.edit { prefs ->
            prefs.remove(
                longPreferencesKey(assignmentId)
            )
        }
    }

    suspend fun saveAnswer(
        key: String,
        answer: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[stringPreferencesKey(key)] = answer
        }
    }

    suspend fun getAnswer(
        key: String
    ): String? {

        val prefs =
            context.dataStore.data.first()

        return prefs[
            stringPreferencesKey(key)
        ]
    }

    suspend fun clearAnswer(
        key: String
    ) {
        context.dataStore.edit { prefs ->
            prefs.remove(
                stringPreferencesKey(key)
            )
        }
    }
}