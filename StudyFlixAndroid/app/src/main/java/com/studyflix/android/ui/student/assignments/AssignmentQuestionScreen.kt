package com.studyflix.android.ui.student.assignments

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.studyflix.android.domain.model.AssignmentSubmission
import com.studyflix.android.ui.theme.StudentColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import com.studyflix.android.ui.theme.AppColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AssignmentQuestionScreen(
    assignmentId: String,
    onBack: () -> Unit,
    viewModel: AssignmentDetailsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val answers = remember {
        mutableStateMapOf<String, String>()
    }

    var submitted by remember {
        mutableStateOf(false)
    }

    var submitting by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(assignmentId) {
        viewModel.loadAssignment(assignmentId)
    }


    val assignment = uiState.assignment

    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Assignment Questions",
                        color = StudentColors.Primary
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = StudentColors.Primary
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = assignment?.title ?: "Loading...",
                style = MaterialTheme.typography.headlineSmall,
                color = StudentColors.Primary
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "${assignment?.questions?.size ?: 0} Questions",
                style = MaterialTheme.typography.bodyMedium,
                color = StudentColors.Primary
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

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
                        text = "Assessment Summary",
                        color = StudentColors.Primary,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Total Questions: ${assignment?.questions?.size ?: 0}"

                    )

                    Text(
                        text = "Total Marks: ${assignment?.totalMarks ?: 0}"
                    )

                    Text(
                        text = "Duration: ${assignment?.examTime ?: ""}"
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(
                    assignment?.questions ?: emptyList()
                ) { question ->

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
                                text = question.text,
                                color = androidx.compose.ui.graphics.Color.White
                            )


                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = question.text,
                                color = Color.White
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            OutlinedTextField(
                                value = answers[question.number.toString()] ?: "",
                                onValueChange = {
                                    answers[question.number.toString()] = it
                                },
                                modifier = Modifier.fillMaxWidth(),
                                label = {
                                    Text("Your Answer")
                                }
                            )
                        }
                    }
                }
            }

            if (submitting) {

                Text(
                    text = "Submitting assignment...",
                    color = StudentColors.Primary
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }

            if (submitted) {

                Text(
                    text = "✅ Assignment submitted",
                    color = StudentColors.Primary
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }

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
                        text = "Ready to submit?",
                        style = MaterialTheme.typography.titleMedium,
                        color = StudentColors.Primary
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Review your answers before submitting."
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Button(
                        enabled = !submitted && !submitting,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = StudentColors.Primary
                        ),
                        onClick = {

                            val submission = AssignmentSubmission(
                                assignmentId = assignmentId,
                                studentId = FirebaseAuth.getInstance()
                                    .currentUser
                                    ?.uid
                                    .orEmpty(),
                                submittedAt = System.currentTimeMillis(),
                                answers = answers.toMap()
                            )

                            submitting = true

                            viewModel.submitAssignment(
                                submission
                            )

                            submitted = true
                            submitting = false
                        }
                    ) {
                        Text(
                            text = "Submit Assignment",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}