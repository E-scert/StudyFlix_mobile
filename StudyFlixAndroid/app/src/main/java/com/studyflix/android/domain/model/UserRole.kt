package com.studyflix.android.domain.model

/**
 * Mirrors the three Firestore collections used by the web app to gate access:
 * `admins`, `teachers`, `students` (see public/shared/role-manager.js).
 * A user's role is resolved by checking which collection contains a document
 * whose id equals their Firebase Auth uid.
 */
enum class UserRole(val collectionName: String) {
    ADMIN("admins"),
    TEACHER("teachers"),
    STUDENT("students");

    companion object {
        fun fromCollectionName(name: String): UserRole? =
            entries.firstOrNull { it.collectionName == name }
    }
}
