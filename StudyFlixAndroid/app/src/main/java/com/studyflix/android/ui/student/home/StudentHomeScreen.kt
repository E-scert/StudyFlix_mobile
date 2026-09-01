package com.studyflix.android.ui.student.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.studyflix.android.core.ui.theme.StudyFlixBackground
import com.studyflix.android.domain.model.AccountStatus


/** Equivalent of public/student/home.html: quick links into every student feature module. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentHomeScreen(
    onOpenVideos: () -> Unit,
    onOpenQuizzes: () -> Unit,
    onOpenMarks: () -> Unit,
    onOpenChat: () -> Unit,
    onLogout: () -> Unit,
    viewModel: StudentHomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = StudyFlixBackground,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFF0F172A)

                ),
                title = {
                    Column {
                        Text("StudyFlix", color = Color(0xFF7C4DFF))

                        Text(
                            text = "Student Portal",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF7C4DFF)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.logout()
                            onLogout()
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Log out")
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Welcome back,",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = uiState.student?.name ?: "Student",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF7C4DFF)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${uiState.student?.grade ?: ""} • ${uiState.student?.school ?: ""}"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Plan: ${uiState.student?.subscription ?: "Trial"}",
                        style = MaterialTheme.typography.bodySmall
                    )

                    val statusColor = when (uiState.student?.status) {
                        AccountStatus.APPROVED -> Color(0xFF4CAF50)
                        AccountStatus.PENDING -> Color(0xFFFFB300)
                        AccountStatus.SUSPENDED -> Color(0xFFE53935)
                        else -> Color.Gray
                    }

                    Text(
                        text = "Status: ${uiState.student?.status?.name ?: "Pending"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = statusColor
                    )
                }
            }

            HomeMenuCard(
                "Learning Videos",
                "Watch lessons and study content",
                Icons.Filled.PlayCircle,
                onOpenVideos
            )

            HomeMenuCard(
                "Practice Quizzes",
                "Test your understanding",
                Icons.Filled.Quiz,
                onOpenQuizzes
            )

            HomeMenuCard(
                "Academic Results",
                "View marks and performance",
                Icons.Filled.Grade,
                onOpenMarks
            )

            HomeMenuCard(
                "Messages",
                "Communicate with teachers",
                Icons.AutoMirrored.Filled.Chat,
                onOpenChat
            )
        }
    }
}

@Composable
private fun HomeMenuCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1F38)
        ),
        onClick = onClick
    ) {
        ListItem(
            headlineContent = {
                Text(title)
            },

            supportingContent = {
                Text(subtitle)
            },

            leadingContent = {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color(0xFF7C4DFF)
                )
            },

            trailingContent = {
                Text(
                    text = "›",
                    color = Color(0xFF7C4DFF),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        )
    }
}
