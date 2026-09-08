package com.studyflix.android.ui.teacher.assignments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TeacherAssignmentsScreen(
    onOpenCreateAssignment: () -> Unit,
    onOpenAssignment: (String) -> Unit,
    viewModel: TeacherAssignmentsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        CircularProgressIndicator()
        return
    }


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            Button(
                onClick = onOpenCreateAssignment
            ) {
                Text("Create Assignment")
            }
        }

        items(uiState.assignments) { assignment ->

            ListItem(
                modifier = Modifier.clickable {
                    onOpenAssignment(assignment.id)
                },
                headlineContent = {
                    Text(assignment.title)
                },
                supportingContent = {
                    Text(
                        "${assignment.subject} • ${assignment.grade}"
                    )
                },
                trailingContent = {
                    Text(assignment.status)
                }
            )
        }
    }
}