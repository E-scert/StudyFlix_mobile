package com.studyflix.android.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.studyflix.android.domain.model.UserRole
import com.studyflix.android.ui.admin.AdminDashboardScreen
import com.studyflix.android.ui.auth.LoginScreen
import com.studyflix.android.ui.auth.SignUpStudentScreen
import com.studyflix.android.ui.landing.LandingScreen
import com.studyflix.android.ui.student.chat.ChatScreen
import com.studyflix.android.ui.student.home.StudentHomeScreen
import com.studyflix.android.ui.student.marks.MarksScreen
import com.studyflix.android.ui.student.notes.Note
import com.studyflix.android.ui.student.notes.NoteDetailScreen
import com.studyflix.android.ui.student.notes.NotesScreen
import com.studyflix.android.ui.student.notes.NotesViewModel
import com.studyflix.android.ui.student.quizzes.QuizzesScreen
import com.studyflix.android.ui.student.quizzes.TakeQuizScreen
import com.studyflix.android.ui.student.videos.VideoPlayerScreen
import com.studyflix.android.ui.student.videos.VideosScreen
import com.studyflix.android.ui.teacher.TeacherDashboardScreen


/**
 * Root navigation graph. On successful sign-in, the app pushes the
 * caller to the correct portal root -- equivalent to
 * redirectToRoleBasedDashboard() in public/shared/role-manager.js.
 */
@Composable
fun StudyFlixNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Landing.route) {

        composable(Screen.Landing.route) {
            LandingScreen(
                onStudentClick = {
                    navController.navigate(Screen.Login.createRoute("student"))
                },
                onTeacherClick = {
                    navController.navigate(Screen.Login.createRoute("teacher"))
                },
                onAdminClick = {
                    navController.navigate(Screen.Login.createRoute("admin"))
                },

            )
        }

        composable(
            route = Screen.Login.route,
            arguments = listOf(
                navArgument(Screen.Login.ARG_PORTAL) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val portal =
                backStackEntry.arguments
                    ?.getString(Screen.Login.ARG_PORTAL)
                    ?: "student"

            LoginScreen(
                portal = portal,

                onSignedIn = { role ->
                    navigateToPortalRoot(navController, role)
                },

                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUpStudent.route)
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.SignUpStudent.route) {
            SignUpStudentScreen(
                onSignedUp = {

                    // After successful registration,
                    // return to Login instead of Student Home

                    navController.navigate(
                        Screen.Login.createRoute("student")
                    ) {
                        popUpTo(Screen.SignUpStudent.route) {
                            inclusive = true
                        }
                    }
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }


                        // ---- Student portal ----
        composable(Screen.StudentHome.route) {
            StudentHomeScreen(
                onOpenVideos = { navController.navigate(Screen.StudentVideos.route) },
                onOpenNotes = {
                    navController.navigate(
                        Screen.StudentNotes.route
                    )
                },
                onOpenQuizzes = { navController.navigate(Screen.StudentQuizzes.route) },
                onOpenMarks = { navController.navigate(Screen.StudentMarks.route) },
                onOpenChat = { navController.navigate(Screen.StudentChat.route) },
                onLogout = {
                    navController.navigate(Screen.Landing.route) {
                        popUpTo(0)
                    }
                 }
            )
        }
        composable(
            Screen.StudentVideos.route
        ) {

            VideosScreen(
                onBack = {
                    navController.popBackStack()
                },
                onOpenVideo = { videoUrl ->

                    val encodedUrl =
                        java.net.URLEncoder.encode(
                            videoUrl,
                            "UTF-8"
                        )

                    navController.navigate(
                        "student_video_player/$encodedUrl"
                    )
                }
            )
        }

        composable(
            route = "student_video_player/{videoUrl}",
            arguments = listOf(
                navArgument("videoUrl") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val videoUrl =
                backStackEntry.arguments?.getString("videoUrl")
                    ?: ""

            VideoPlayerScreen(
                videoUrl = java.net.URLDecoder.decode(
                    videoUrl,
                    "UTF-8"
                )
            )
        }

        composable(
            Screen.StudentNotes.route
        ) {

            NotesScreen(
                onBack = {
                    navController.popBackStack()
                },

                onOpenNote = { noteId ->
                    navController.navigate(
                        Screen.StudentNoteDetail.createRoute(noteId)
                    )
                }
            )
        }
        composable(
            route = Screen.StudentNoteDetail.route,
            arguments = listOf(
                navArgument(
                    Screen.StudentNoteDetail.ARG_NOTE_ID
                ) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val noteId =
                backStackEntry.arguments?.getString(
                    Screen.StudentNoteDetail.ARG_NOTE_ID
                ) ?: ""

            val viewModel: NotesViewModel = hiltViewModel()

            val note = viewModel.getNoteById(noteId)

            if (note != null) {

                NoteDetailScreen(
                    note = note,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(Screen.StudentQuizzes.route) {
            QuizzesScreen(
                onBack = { navController.popBackStack() },
                onOpenQuiz = { quizId -> navController.navigate(Screen.TakeQuiz.createRoute(quizId)) }
            )
        }
        composable(
            route = Screen.TakeQuiz.route,
            arguments = listOf(navArgument(Screen.TakeQuiz.ARG_QUIZ_ID) { type = NavType.StringType })
        ) {
            TakeQuizScreen(onFinished = { navController.popBackStack() })
        }
        composable(Screen.StudentMarks.route) { MarksScreen(onBack = { navController.popBackStack() }) }
        composable(Screen.StudentChat.route) { ChatScreen(onBack = { navController.popBackStack() }) }

        // ---- Teacher / Admin portal roots ----
        composable(Screen.TeacherDashboard.route) {
            TeacherDashboardScreen {
                navController.navigate(Screen.Login.route) {
                    popUpTo(0)
                }
            }
        }
        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}

private fun navigateToPortalRoot(navController: NavHostController, role: UserRole) {
    val destination = when (role) {
        UserRole.STUDENT -> Screen.StudentHome.route
        UserRole.TEACHER -> Screen.TeacherDashboard.route
        UserRole.ADMIN -> Screen.AdminDashboard.route
    }
    navController.navigate(destination) {
        popUpTo(Screen.Login.route) { inclusive = true }
    }
}
