package com.studyflix.android.ui.teacher.learners

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TeacherLearnerAssignmentsScreen(
    learnerId: String,
    viewModel: TeacherLearnerAssignmentsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(learnerId) {
        viewModel.loadAssignments(learnerId)
    }

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
            Text(
                text = "Assignments Loaded: ${uiState.assignments.size}"
            )
        }

        items(uiState.assignments) { assignment ->

            ListItem(
                headlineContent = {
                    Text(assignment.title)
                },
                supportingContent = {
                    Text("Due: ${assignment.dueDate}")
                },
                trailingContent = {
                    Text(
                        if (assignment.submitted)
                            "Submitted"
                        else
                            "Not Submitted"
                    )
                }
            )
        }
    }

}