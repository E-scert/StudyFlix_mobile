package com.studyflix.android.ui.teacher.assignments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TeacherCreateAssignmentScreen(
    viewModel: TeacherCreateAssignmentViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Create Assignment"
        )

        OutlinedTextField(
            value = uiState.title,
            onValueChange = viewModel::updateTitle,
            label = {
                Text("Title")
            }
        )

        OutlinedTextField(
            value = uiState.subject,
            onValueChange = viewModel::updateSubject,
            label = {
                Text("Subject")
            }
        )

        OutlinedTextField(
            value = uiState.totalMarks,
            onValueChange = viewModel::updateMarks,
            label = {
                Text("Total Marks")
            }
        )

        OutlinedTextField(
            value = uiState.dueDate,
            onValueChange = viewModel::updateDueDate,
            label = {
                Text("Due Date")
            }
        )

        Button(
            onClick = {
                viewModel.publishAssignment()
            }
        ) {
            Text("Publish Assignment")
        }
    }
}