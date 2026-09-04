package com.studyflix.android.ui.student.assignments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.studyflix.android.ui.theme.AppColors
import com.studyflix.android.ui.theme.StudentColors
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentSubmissionScreen(
    assignmentId: String,
    onBack: () -> Unit,
    viewModel: AssignmentDetailsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val submission = uiState.submission

    LaunchedEffect(assignmentId) {

        viewModel.loadAssignment(
            assignmentId
        )

        FirebaseAuth.getInstance()
            .currentUser
            ?.uid
            ?.let { studentId ->

                viewModel.checkSubmissionStatus(
                    assignmentId,
                    studentId
                )
            }
    }

    val scrollState = rememberScrollState()
    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.TopBar
                ),
                title = {
                    Text(
                        "My Submission",
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

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(padding)
                .padding(16.dp)
        ) {



            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = uiState.assignment?.title ?: "",
                        style = MaterialTheme.typography.titleLarge,
                        color = StudentColors.Primary
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Status: Submitted ✅"
                    )

                    if (uiState.submittedAt != null) {

                        Text(
                            text = "Submitted: ${
                                java.text.SimpleDateFormat(
                                    "dd MMM yyyy HH:mm",
                                    java.util.Locale.getDefault()
                                ).format(
                                    java.util.Date(uiState.submittedAt!!)
                                )
                            }"
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            uiState.assignment?.questions?.forEach { question ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.Card
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Question ${question.number}",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
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

                        Text(
                            text = "My Answer:",
                            color = StudentColors.Primary
                        )
                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text = "Model Answer:",
                            color = StudentColors.Primary
                        )

                        Text(
                            text = uiState.assignment
                                ?.memoPerQuestion
                                ?.getOrNull(question.number - 1)
                                ?: "Memo not available",
                            color = Color.White
                        )

                        Text(
                            text = submission?.answers?.get(
                                question.number.toString()
                            ) ?: "No Answer",
                            color = Color.White
                        )
                    }
                }


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
                        text = "Results Status",
                        color = StudentColors.Primary,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = if (submission?.isMarked == true)
                            "Marked ✅"
                        else
                            "Awaiting Marking ⏳",
                        color = Color.White
                    )

                    if (submission?.isMarked == true) {

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "Score: ${submission.score}",
                            color = Color.White
                        )

                        Text(
                            text = "Percentage: ${submission.percentage}%",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
    }
}