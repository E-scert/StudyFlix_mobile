package com.studyflix.android.core.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * Offline-first helper used by every repository in the `data` layer.
 *
 * Behaviour mirrors what the web app gets "for free" from
 * `db.enablePersistence()` in shared/firebase-config.js: the UI is fed from
 * the local cache first (Room, in this case), a network refresh is attempted
 * in the background, and the cache -- therefore the UI -- is updated once the
 * refresh succeeds. If the device is offline, the cached [Resource.Success]
 * keeps flowing and the failure is reported without wiping existing data.
 *
 * @param query how to observe the local (Room) source of truth.
 * @param fetch how to fetch fresh data from Firestore.
 * @param saveFetchResult how to persist the fresh Firestore data into Room.
 * @param shouldFetch decide whether a network call is warranted (default: always).
 */
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
): Flow<Resource<ResultType>> = flow {
    val cached = query().first()

    if (shouldFetch(cached)) {
        emit(Resource.Loading)
        try {
            saveFetchResult(fetch())
        } catch (t: Throwable) {
            // Keep serving the cache, but let the UI know the refresh failed
            // (e.g. no connectivity) so it can show a subtle offline indicator.
            emit(Resource.Error(t.message ?: "Failed to refresh from server", t))
        }
    }

    emitAll(query().map { Resource.Success(it) })
}
