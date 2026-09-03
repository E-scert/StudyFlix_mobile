package com.studyflix.android.ui.student.assignments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.studyflix.android.ui.theme.StudentColors
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.studyflix.android.domain.model.AssignmentSubmission
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import com.studyflix.android.ui.theme.AppColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentDetailsScreen(
    assignmentId: String,
    onBack: () -> Unit,
    onStartAssignment: (String) -> Unit,
    viewModel: AssignmentDetailsViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    val answers = remember {
        mutableStateMapOf<String, String>()
    }

    LaunchedEffect(assignmentId) {

        viewModel.loadAssignment(
            assignmentId
        )
    }
    val assignment = uiState.assignment

    Scaffold(
        containerColor = AppColors.Background,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Assignment Details",
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
                .verticalScroll(scrollState)
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = assignment?.title ?: "Loading...",
                color = StudentColors.Primary,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(
                modifier = Modifier.height(12.dp)
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
                        text = "Assignment Information",
                        color = StudentColors.Primary,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Text(
                        text = "Subject: ${assignment?.subject ?: ""}",
                        color = androidx.compose.ui.graphics.Color.White
                    )

                    Text(
                        text = "Teacher: ${assignment?.teacherName ?: ""}"
                   ,color = androidx.compose.ui.graphics.Color.White
                    )

                    Text(
                        text = "Examiner: ${assignment?.examiner ?: ""}"
                    ,color = androidx.compose.ui.graphics.Color.White
                    )

                    Text(
                        text = "Marks: ${assignment?.totalMarks ?: 0}"

                    ,color = androidx.compose.ui.graphics.Color.White
                        )

                    Text(
                        text = "Duration: ${assignment?.examTime ?: ""}"
                    ,color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "Due Date: ${assignment?.dueDate ?: ""}"
                    ,color = androidx.compose.ui.graphics.Color.White
                    )

                    Text(
                        text = "Status: ${assignment?.status ?: ""}"
                    ,color = androidx.compose.ui.graphics.Color.White
                    )
                }

            }

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
                        text = "Instructions",
                        color = StudentColors.Primary,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    assignment?.instructions?.forEach { instruction ->

                        Text(
                            text = "• $instruction",
                            color = Color.White,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                    }
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = StudentColors.Primary
                ),
                onClick = {
                    onStartAssignment(
                        assignmentId
                    )
                }
            ) {
                Text(
                    text = "Continue To Assignment",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}