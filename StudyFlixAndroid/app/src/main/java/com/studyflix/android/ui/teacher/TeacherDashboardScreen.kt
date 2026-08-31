package com.studyflix.android.ui.teacher

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Scaffold for the teacher portal root. Follows the same MVVM + Clean
 * Architecture pattern as the student portal (see ui/student/*): a
 * TeacherDashboardViewModel would sit here, backed by domain use cases such
 * as GetLearnersUseCase / GetAssignmentsUseCase / GetTeacherMarksUseCase,
 * each wired to a TeacherRepository following the same shape as
 * StudentRepository. Feature screens (learners, assignments, results,
 * messaging -- see PROJECT_DOCUMENTATION.md section 5.2) can be added under
 * ui/teacher/<feature>/ exactly like ui/student/<feature>/.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDashboardScreen(onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Teacher Dashboard") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Filled.Logout, contentDescription = "Log out")
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
                text = "Learner management, assignments, marks and messaging modules " +
                    "plug in here using the same repository/use case pattern as the " +
                    "student portal.",
                style = MaterialTheme.typography.bodyMedium
            )
            ListItem(headlineContent = { Text("My Learners") })
            ListItem(headlineContent = { Text("Assignments") })
            ListItem(headlineContent = { Text("Marks & Results") })
            ListItem(headlineContent = { Text("Messaging") })
        }
    }
}
