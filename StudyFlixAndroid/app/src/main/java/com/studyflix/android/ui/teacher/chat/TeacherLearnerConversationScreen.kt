package com.studyflix.android.ui.teacher.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.studyflix.android.ui.theme.AppColors.Card

@Composable
fun TeacherLearnerConversationScreen(
    learnerId: String,
    viewModel: TeacherConversationViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(learnerId) {
        viewModel.loadMessages(learnerId)
    }

    if (uiState.isLoading) {
        CircularProgressIndicator()
        return
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(uiState.messages) { message ->

                val isMine =
                    message.senderId != learnerId

                Row(
                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        if (isMine)
                            Arrangement.End
                        else
                            Arrangement.Start
                ) {

                    Card {

                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {

                            Text(
                                text = message.text
                            )

                            Text(
                                text = java.text.SimpleDateFormat(
                                    "HH:mm",
                                    java.util.Locale.getDefault()
                                ).format(
                                    java.util.Date(message.timestamp)
                                ),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            OutlinedTextField(
                value = uiState.draft,
                onValueChange = viewModel::onDraftChange,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    viewModel.sendMessage(
                        learnerId
                    )
                }
            ) {
                Text("Send")
            }
        }
    }
}