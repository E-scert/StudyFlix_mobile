package com.studyflix.android.ui.teacher.overview

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TeacherOverviewScreen(
    viewModel: TeacherOverviewViewModel = hiltViewModel()

) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Overview",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Total Learners: ${uiState.totalLearners}"
        )

        Text(
            text = "Online Learners: ${uiState.onlineLearners}"
        )

        Text(
            text = "Active Assignments: ${uiState.activeAssignments}"
        )
    }
}