package com.studyflix.android.ui.teacher

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
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


//  Scaffold for the teacher portal root. Follows the same MVVM + Clean
//  Architecture pattern as the student portal (see ui/student): a
//  TeacherDashboardViewModel would sit here, backed by domain use cases such
// as GetLearnersUseCase / GetAssignmentsUseCase / GetTeacherMarksUseCase,
// each wired to a TeacherRepository following the same shape as
//  StudentRepository. Feature screens (learners, assignments, results,
// messaging -- see PROJECT_DOCUMENTATION.md section 5.2) can be added under
// ui/teacher/<feature>/ exactly like ui/student/<feature>/.
//

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDashboardScreen(
    onLogout: () -> Unit,
    onOpenOverview: () -> Unit,
    onOpenLearners: () -> Unit,
    onOpenLearnersChat: () -> Unit,
    onOpenCreateAssignment: () -> Unit,

    viewModel: TeacherDashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Teacher Dashboard") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Log out")
                    }
                }
            )
        }
    ) { padding: PaddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = uiState.teacher?.name
                    ?: "Loading Teacher...",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = uiState.teacher?.schoolName
                    ?: "",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = uiState.teacher?.selectedSubject
                    ?: "",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = uiState.teacher?.selectedGrade
                    ?: "",
                style = MaterialTheme.typography.bodyMedium
            )

            ListItem(
                headlineContent = {
                    Text("Overview")
                },
                modifier = Modifier.clickable(
                    onClick = onOpenOverview
                )
            )

            ListItem(
                headlineContent = {
                    Text("My Learners")
                },

                modifier = Modifier.clickable(
                    onClick = onOpenLearners
                )
            )

            ListItem(
                headlineContent = {
                    Text("Assignments")
                },
                modifier = Modifier.clickable(
                    onClick = onOpenCreateAssignment
                )
            )

            ListItem(
                headlineContent = {
                    Text("Student Submissions")
                }
            )

            ListItem(
                headlineContent = {
                    Text("Marks & Results")
                }
            )

            ListItem(
                headlineContent = {
                    Text("Learners Chat")
                },
                modifier = Modifier.clickable(
                    onClick = onOpenLearnersChat
                )
            )


            ListItem(
                headlineContent = {
                    Text("Admin Chat")
                }
            )

            ListItem(
                headlineContent = {
                    Text("Settings")
                }
            )

        }
    }
}
