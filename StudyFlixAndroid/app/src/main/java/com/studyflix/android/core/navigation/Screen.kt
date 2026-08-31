package com.studyflix.android.core.navigation

/**
 * Type-safe route definitions. Mirrors the page map in
 * PROJECT_DOCUMENTATION.md section 5, one route per portal screen that has
 * an Android equivalent so far.
 */
sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object SignUpStudent : Screen("signup_student")

    // Student portal
    data object StudentHome : Screen("student/home")
    data object StudentVideos : Screen("student/videos")
    data object StudentQuizzes : Screen("student/quizzes")
    data object TakeQuiz : Screen("student/quiz/{quizId}") {
        fun createRoute(quizId: String) = "student/quiz/$quizId"
        const val ARG_QUIZ_ID = "quizId"
    }
    data object StudentMarks : Screen("student/marks")
    data object StudentChat : Screen("student/chat")

    // Teacher / Admin portals (dashboards scaffolded; see ui/teacher, ui/admin)
    data object TeacherDashboard : Screen("teacher/dashboard")
    data object AdminDashboard : Screen("admin/dashboard")
}
