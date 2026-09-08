package com.studyflix.android.ui.teacher.learners

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TeacherLearnerProfileScreen(
    learnerId: String,
    viewModel: TeacherLearnerProfileViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(learnerId) {
        viewModel.loadLearner(learnerId)
    }

    if (uiState.isLoading) {
        CircularProgressIndicator()
        return
    }

    val learner = uiState.learner ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = learner.name,
            style = MaterialTheme.typography.headlineSmall
        )

        Text("Email: ${learner.email}")

        Text("Phone: ${learner.phone}")

        Text("School: ${learner.school}")

        Text("Grade: ${learner.grade}")

        Text("Plan: ${learner.plan}")
    }
}