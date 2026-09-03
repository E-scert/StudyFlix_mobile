package com.studyflix.android.ui.student.home

import androidx.compose.material.icons.filled.Assignment
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
import androidx.compose.material.icons.filled.Description
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
import com.studyflix.android.ui.theme.AppColors
import com.studyflix.android.ui.theme.StudentColors
import androidx.compose.material.icons.filled.Folder
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items


data class HomeMenuItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)



/** Equivalent of public/student/home.html: quick links into every student feature module. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentHomeScreen(
    onOpenVideos: () -> Unit,
    onOpenNotes: () -> Unit,
    onOpenAssignments: () -> Unit,
    onOpenQuizzes: () -> Unit,
    onOpenPastPapers: () -> Unit,
    onOpenMarks: () -> Unit,
    onOpenChat: () -> Unit,
    onLogout: () -> Unit,
    viewModel: StudentHomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    val menuItems = listOf(
        HomeMenuItem(
            "Videos",
            "Watch lessons",
            Icons.Filled.PlayCircle,
            onOpenVideos
        ),
        HomeMenuItem(
            "Notes",
            "Study material",
            Icons.Filled.Description,
            onOpenNotes
        ),
        HomeMenuItem(
            "Assignments",
            "School work",
            Icons.Filled.Assignment,
            onOpenAssignments
        ),
        HomeMenuItem(
            "Quizzes",
            "Practice tests",
            Icons.Filled.Quiz,
            onOpenQuizzes
        ),
        HomeMenuItem(
            "Past Papers",
            "Exam papers",
            Icons.Filled.Folder,
            onOpenPastPapers
        ),
        HomeMenuItem(
            "Results",
            "Academic marks",
            Icons.Filled.Grade,
            onOpenMarks
        ),
        HomeMenuItem(
            "Messages",
            "Teacher chat",
            Icons.AutoMirrored.Filled.Chat,
            onOpenChat
        )
    )

    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.TopBar
                ),
                title = {
                    Column {
                        Text(
                            "StudyFlix",
                            color = StudentColors.Primary
                        )

                        Text(
                            text = "Student Portal",
                            style = MaterialTheme.typography.labelSmall,
                            color = StudentColors.Primary
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
                        Icon(
                            Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Log out"
                        )
                    }
                }
            )
        }
    )
     { padding: PaddingValues ->
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
                        color = StudentColors.Primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${uiState.student?.grade ?: ""} • ${uiState.student?.school ?: ""}"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Plan: ${uiState.student?.subscription}",
                        style = MaterialTheme.typography.bodySmall
                    )

                    val statusColor = when (uiState.student?.status) {
                        AccountStatus.APPROVED -> AppColors.Success
                        AccountStatus.PENDING -> AppColors.Warning
                        AccountStatus.SUSPENDED -> AppColors.Suspended
                        else -> Color.Gray
                    }

                    Text(
                        text = "Status: ${uiState.student?.status?.name ?: "Pending"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = statusColor
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(menuItems) { item ->

                    HomeGridCard(
                        title = item.title,
                        icon = item.icon,
                        onClick = item.onClick
                    )
                }
            }

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
            containerColor = AppColors.Card

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
                    tint = StudentColors.Primary
                )
            },

            trailingContent = {
                Text(
                    text = "›",
                    color = StudentColors.Primary,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        )
    }
}

@Composable
private fun HomeGridCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Card
        ),
        onClick = onClick
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = StudentColors.Primary
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = title,
                color = Color.White
            )
        }
    }
}