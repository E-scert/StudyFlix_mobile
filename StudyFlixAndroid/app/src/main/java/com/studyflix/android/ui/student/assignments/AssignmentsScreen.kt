package com.studyflix.android.ui.student.assignments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.studyflix.android.ui.theme.AppColors
import com.studyflix.android.ui.theme.StudentColors
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentsScreen(
    onBack: () -> Unit,
    onOpenAssignment: (String) -> Unit,
    viewModel: AssignmentsViewModel = hiltViewModel()
){

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.TopBar
                ),
                title = {
                    Text(
                        "Assignments",
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
    ) { padding: PaddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(uiState.assignments) { assignment ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onOpenAssignment(
                            assignment.id
                        )
                    }
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = assignment.title,
                            color = StudentColors.Primary
                        )

                        Text(
                            text = assignment.subject
                        )

                        Text(
                            text = "Teacher: ${assignment.teacherName}"
                        )

                        Text(
                            text = "Marks: ${assignment.totalMarks}"
                        )

                        Text(
                            text = "Status: ${assignment.status}",
                            color = when (assignment.status.lowercase()) {
                                "active" -> Color.Green
                                else -> Color.Red
                            }
                        )
                    }
                }
            }
        }
    }
}