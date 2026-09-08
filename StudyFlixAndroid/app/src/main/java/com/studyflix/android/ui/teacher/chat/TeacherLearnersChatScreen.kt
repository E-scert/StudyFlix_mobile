package com.studyflix.android.ui.teacher.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TeacherLearnersChatScreen(
    onOpenConversation: (String) -> Unit,
    viewModel: TeacherLearnersChatViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        CircularProgressIndicator()
        return
    }

    LazyColumn {

        items(uiState.learners) { learner ->

            ListItem(
                modifier = Modifier.clickable(
                    onClick = {
                        onOpenConversation(
                            learner.id
                        )
                    }
                ),
                headlineContent = {
                    Text(learner.name)
                },

                supportingContent = {
                    Text(
                        if (learner.online)
                            "Online"
                        else
                            "Offline"
                    )
                }
            )
        }
    }
}
