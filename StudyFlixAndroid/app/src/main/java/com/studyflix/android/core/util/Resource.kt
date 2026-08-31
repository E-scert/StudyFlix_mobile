package com.studyflix.android.core.util

/**
 * Generic wrapper for anything that comes back from a repository, so the UI layer
 * can render loading/error/success states uniformly across every screen.
 */
sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : Resource<Nothing>()
}

/** Convenience mapper so ViewModels can transform the payload without unwrapping manually. */
inline fun <T, R> Resource<T>.map(transform: (T) -> R): Resource<R> = when (this) {
    is Resource.Loading -> Resource.Loading
    is Resource.Success -> Resource.Success(transform(data))
    is Resource.Error -> Resource.Error(message, throwable)
}
