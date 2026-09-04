package com.studyflix.android.ui.student.assignments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.studyflix.android.ui.theme.StudentColors
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import com.studyflix.android.ui.theme.AppColors
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentMemoScreen(
    assignmentId: String,
    onBack: () -> Unit,
    viewModel: AssignmentDetailsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val assignment = uiState.assignment

    LaunchedEffect(assignmentId) {
        viewModel.loadAssignment(
            assignmentId
        )
    }



    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val assignment = uiState.assignment

        if (assignment == null) {

            Text("Loading Memo...")

        } else if (!assignment.memoPublished) {

            Text("Memo Not Yet Published")

        } else {

            Scaffold(
                containerColor = AppColors.Background,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Assignment Memo",
                                color = StudentColors.Primary
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = onBack
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                }
            ) { padding ->

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    item {

                        Text(
                            text = assignment.title,
                            style = MaterialTheme.typography.headlineSmall,
                            color = StudentColors.Primary
                        )

                    }
                    item {

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = AppColors.Card
                            )
                        ) {

                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {

                                Text(
                                    text = "📖 Memo Published",
                                    color = StudentColors.Primary,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(
                                    modifier = Modifier.height(12.dp)
                                )

                                Text(
                                    text = "Subject: ${assignment.subject}",
                                    color = Color.White
                                )

                                Text(
                                    text = "Questions: ${assignment.questions.size}",
                                    color = Color.White
                                )

                                Text(
                                    text = "Total Marks: ${assignment.totalMarks}",
                                    color = Color.White
                                )

                            }
                        }
                    }

                    items(
                        assignment.memoPerQuestion.size
                    ) { index ->

                        val memo = assignment.memoPerQuestion[index]

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = AppColors.Card
                            )
                        ) {


                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {


                                Text(
                                    text = "Question ${index + 1}",
                                    color = StudentColors.Primary,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )

                                Text(
                                    text = assignment.questions
                                        .getOrNull(index)
                                        ?.text
                                        ?: "Question not available",
                                    color = Color.White
                                )

                                Spacer(
                                    modifier = Modifier.height(12.dp)
                                )

                                Text(
                                    text = "✅ Model Answer",
                                    color = StudentColors.Primary,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(
                                    text = memo,
                                    color = Color.White
                                )

                            }
                        }
                    }
                }
            }

        }    }
}

