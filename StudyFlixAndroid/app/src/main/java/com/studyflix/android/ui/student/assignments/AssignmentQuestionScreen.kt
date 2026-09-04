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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color



@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AssignmentQuestionScreen(
    assignmentId: String,
    onBack: () -> Unit,
    viewModel: AssignmentDetailsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val answers = rememberSaveable {
        mutableStateMapOf<String, String>()
    }

    var submitted by remember {
        mutableStateOf(false)
    }

    var showSubmitDialog by remember {
        mutableStateOf(false)
    }

    var submitting by remember {
        mutableStateOf(false)
    }

    var timeLeft by rememberSaveable {
        mutableStateOf(0L)
    }

    var showTimeExpiredDialog by remember {
        mutableStateOf(false)
    }

    val assignment = uiState.assignment

    LaunchedEffect(assignmentId) {
        viewModel.loadAssignment(assignmentId)

    }
    LaunchedEffect(assignment?.duration) {

        if (
            assignment != null &&
            timeLeft == 0L
        ) {
            timeLeft =
                assignment.duration * 60 * 1000L
        }
    }
    LaunchedEffect(timeLeft) {

        while (timeLeft > 0) {
            kotlinx.coroutines.delay(1000)
            timeLeft -= 1000
        }
    }

    LaunchedEffect(timeLeft) {

        if (
            timeLeft == 0L &&
            !submitted
        ) {

            showTimeExpiredDialog = true
        }
    }


    val hours = timeLeft / 1000 / 60 / 60
    val minutes = (timeLeft / 1000 / 60) % 60
    val seconds = (timeLeft / 1000) % 60

    val formattedTime =
        String.format(
            "%02d:%02d:%02d",
            hours,
            minutes,
            seconds
        )


    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.TopBar
                ),
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
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "⏰ Time Remaining: $formattedTime",
                color = Color.Red,
                style = MaterialTheme.typography.titleMedium
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
                        text = "Total Questions: ${assignment?.questions?.size ?: 0}",
                        color = Color.White
                    )
                    Text(
                        text = "Status: Active ",
                        color = Color.White
                    )

                    Text(
                        text = "Answered: ${
                            answers.count { it.value.isNotBlank() }
                        } / ${assignment?.questions?.size ?: 0}",
                        color = Color.White
                    )

                    Text(
                        text = "Total Marks: ${assignment?.totalMarks ?: 0}",
                        color = Color.White
                    )

                    Text(
                        text = "Duration: ${assignment?.examTime ?: ""}",
                        color = Color.White
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(20.dp)
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
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = "Question ${question.number}",
                                color = StudentColors.Primary,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Text(
                                text = "Marks: ${question.marks}",
                                color = StudentColors.Primary
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
                                    Text("Your Answer",color = androidx.compose.ui.graphics.Color.White)
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
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.Card
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Button(
                        enabled = !submitted && !submitting,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = StudentColors.Primary
                        ),
                        onClick = {
                            showSubmitDialog = true

                        }

                    ) {
                        Text(
                            text = "Submit Assignment",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    if (showSubmitDialog) {

                        AlertDialog(
                            onDismissRequest = {
                                showSubmitDialog = false
                            },

                            title = {
                                Text("Submit Assignment")
                            },

                            text = {
                                Text(
                                    "Are you sure you want to submit your assignment? You will not be able to edit it afterwards."
                                )
                            },

                            confirmButton = {
                                Button(
                                    onClick = {

                                        showSubmitDialog = false
                                        val submission = AssignmentSubmission(
                                assignmentId = assignmentId,
                                assignmentTitle = assignment?.title ?: "",


                                studentId = FirebaseAuth.getInstance()
                                    .currentUser
                                    ?.uid
                                    .orEmpty(),

                                studentName = FirebaseAuth.getInstance()
                                    .currentUser
                                    ?.displayName
                                    ?: "Unknown Student",
                                startedAt = System.currentTimeMillis(),
                                submittedAt = System.currentTimeMillis(),

                                answers = answers.toMap(),

                                isMarked = false,
                                score = 0,
                                feedback = ""
                            )

                            submitting = true

                            viewModel.submitAssignment(
                                submission
                            )

                            submitted = true
                            submitting = false
                                    }
                                ) {
                                    Text("Submit")
                                }
                            },

                            dismissButton = {
                                Button(
                                    onClick = {
                                        showSubmitDialog = false
                                    }
                                ) {
                                    Text("Cancel")
                                }
                            }
                        )
                    }

                    if (showTimeExpiredDialog) {

                        AlertDialog(
                            onDismissRequest = {},

                            title = {
                                Text("Time Expired")
                            },

                            text = {
                                Text(
                                    "Your assignment time has expired. Your work will now be submitted."
                                )
                            },

                            confirmButton = {
                                Button(
                                    onClick = {

                                        showTimeExpiredDialog = false

                                        showTimeExpiredDialog = false

                                        val submission = AssignmentSubmission(
                                            assignmentId = assignmentId,
                                            assignmentTitle = assignment?.title ?: "",

                                            studentId = FirebaseAuth.getInstance()
                                                .currentUser
                                                ?.uid
                                                .orEmpty(),

                                            studentName = FirebaseAuth.getInstance()
                                                .currentUser
                                                ?.displayName
                                                ?: "Unknown Student",

                                            startedAt = System.currentTimeMillis(),
                                            submittedAt = System.currentTimeMillis(),

                                            answers = answers.toMap(),

                                            isMarked = false,
                                            score = 0,
                                            feedback = ""
                                        )

                                        submitting = true

                                        viewModel.submitAssignment(
                                            submission
                                        )

                                        submitted = true
                                        submitting = false
                                    }
                                ) {
                                    Text("OK")
                                }
                            }
                        )
                    }

                }
            }
        }
    }
}