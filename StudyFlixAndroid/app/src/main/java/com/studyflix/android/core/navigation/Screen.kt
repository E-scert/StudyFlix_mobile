package com.studyflix.android.core.navigation

/**
 * Type-safe route definitions. Mirrors the page map in
 * PROJECT_DOCUMENTATION.md section 5, one route per portal screen that has
 * an Android equivalent so far.
 */
sealed class Screen(val route: String) {

    // Landing page
    data object Landing : Screen("landing")

    // Authentication
    data object Login : Screen("login/{portal}"){
        fun createRoute(portal: String )= "login/$portal"
        const val ARG_PORTAL = "portal"
    }
    data object SignUpStudent : Screen("signup_student")

    // Student portal
    data object StudentHome : Screen("student/home")
    data object StudentVideos : Screen("student/videos")
    data object StudentQuizzes : Screen("student/quizzes")
    object StudentPastPapers : Screen("student_past_papers"
    )

    data object StudentNotes : Screen("student/notes")

    data object StudentNoteDetail : Screen("student/note/{noteId}") {
        fun createRoute(noteId: String) = "student/note/$noteId"
        const val ARG_NOTE_ID = "noteId"
    }
    data object TakeQuiz : Screen("student/quiz/{quizId}") {
        fun createRoute(quizId: String) = "student/quiz/$quizId"
        const val ARG_QUIZ_ID = "quizId"
    }

    data object StudentMarks : Screen("student/marks")
    data object StudentChat : Screen("student/chat")
    object StudentAssignments : Screen("student_assignments")

    object StudentVideoPlayer : Screen(
        "student_video_player/{videoUrl}"
    ) {
        fun createRoute(videoUrl: String): String {
            return "student_video_player/$videoUrl"
        }
    }
    object AssignmentDetails : Screen(
        "assignment_details/{assignmentId}"
    ) {

        fun createRoute(
            assignmentId: String
        ) = "assignment_details/$assignmentId"
    }

    object AssignmentQuestions : Screen(
        "assignment_questions/{assignmentId}"
    ) {

        fun createRoute(
            assignmentId: String
        ) = "assignment_questions/$assignmentId"
    }
    // Teacher portal
    data object TeacherDashboard : Screen("teacher/dashboard")

    // Admin portal
    data object AdminDashboard : Screen("admin/dashboard")


}