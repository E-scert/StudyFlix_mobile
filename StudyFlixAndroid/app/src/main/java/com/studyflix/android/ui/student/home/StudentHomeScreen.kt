package com.studyflix.android.ui.student.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

/** Equivalent of public/student/home.html: quick links into every student feature module. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentHomeScreen(
    onOpenVideos: () -> Unit,
    onOpenQuizzes: () -> Unit,
    onOpenMarks: () -> Unit,
    onOpenChat: () -> Unit,
    onLogout: () -> Unit,
    viewModel: StudentHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.student?.name?.ifBlank { "StudyFlix" } ?: "StudyFlix") },
                actions = {
                    IconButton(onClick = {
                        viewModel.logout()
                        onLogout()
                    }) {
                        Icon(Icons.Filled.Logout, contentDescription = "Log out")
                    }
                }
            )
        }
    ) { padding: PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            uiState.student?.let { student ->
                Text(
                    text = "${student.grade} • ${student.school.ifBlank { "No school set" }}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Plan: ${student.subscription} • Status: ${student.status.name.lowercase()}",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            HomeMenuCard("Videos & Notes", Icons.Filled.PlayCircle, onOpenVideos)
            HomeMenuCard("Quizzes", Icons.Filled.Quiz, onOpenQuizzes)
            HomeMenuCard("Marks & Results", Icons.Filled.Grade, onOpenMarks)
            HomeMenuCard("Chat with Teacher", Icons.Filled.Chat, onOpenChat)
        }
    }
}

@Composable
private fun HomeMenuCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        ListItem(
            headlineContent = { Text(title) },
            leadingContent = { Icon(icon, contentDescription = null) }
        )
    }
}
