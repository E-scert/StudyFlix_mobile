package com.studyflix.android.ui.teacher.learners

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TeacherLearnersScreen(
    onOpenProfile: (String) -> Unit,
    onOpenAssignments: (String) -> Unit,
    viewModel: TeacherLearnersViewModel = hiltViewModel()
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

            Text(
                text = "My Learners",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        items(uiState.learners) { learner ->

            ListItem(
                headlineContent = {
                    Text(learner.name)
                },

                supportingContent = {
                    Text(
                        learner.email
                    )
                },

                trailingContent = {
                    Text(
                        if (learner.online)
                            "Online"
                        else
                            "Offline"
                    )
                }

            )
            Button(
                onClick = {
                    onOpenProfile(
                        learner.id
                    )
                }
            ) {
                Text("Profile")
            }
            Button(
                onClick = {
                    onOpenAssignments(
                        learner.id
                    )
                }
            ) {
                Text("Assignments")
            }
        }
    }
}
