package com.studyflix.android.ui.student.quizzes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.studyflix.android.ui.theme.AppColors
import com.studyflix.android.ui.theme.StudentColors

/** Equivalent of public/student/take-quiz.html: one question at a time, prev/next, submit. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TakeQuizScreen(
    onFinished: () -> Unit,
    viewModel: TakeQuizViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.finalScore) {
        if (uiState.finalScore != null) onFinished()
    }

    val quiz = uiState.quiz

    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.TopBar
                ),
                title = {
                    Text(
                        text = quiz?.title ?: "Quiz",
                        color = StudentColors.Primary
                    )
                }
            )
        }
    ) { padding: PaddingValues ->

        if (quiz == null) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(padding)
                    .padding(24.dp)
            )
            return@Scaffold
        }

        val question = uiState.currentQuestion

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Question ${uiState.currentIndex + 1} of ${quiz.questions.size}",
                style = MaterialTheme.typography.titleSmall,
                color = StudentColors.Primary
            )

            question?.let {

                Text(
                    text = it.text,
                    style = MaterialTheme.typography.headlineSmall,
                    color = AppColors.TextPrimary
                )

                it.options.forEachIndexed { index, option ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = uiState.answers.getOrNull(uiState.currentIndex) == index,
                                onClick = { viewModel.selectAnswer(index) }
                            ),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {

                        RadioButton(
                            selected = uiState.answers.getOrNull(uiState.currentIndex) == index,
                            onClick = { viewModel.selectAnswer(index) }
                        )

                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge,
                            color = AppColors.TextPrimary
                        )
                    }
                }
            }

            uiState.errorMessage?.let {
                Text(
                    text = it,
                    color = AppColors.Error
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                TextButton(
                    onClick = viewModel::previousQuestion,
                    enabled = uiState.currentIndex > 0
                ) {
                    Text(
                        "Previous",
                        color = StudentColors.Primary
                    )
                }

                if (uiState.isLastQuestion) {

                    Button(
                        onClick = viewModel::submit,
                        enabled = !uiState.isSubmitting
                    ) {
                        Text(
                            if (uiState.isSubmitting)
                                "Submitting..."
                            else
                                "Submit"
                        )
                    }

                } else {

                    Button(
                        onClick = viewModel::nextQuestion
                    ) {
                        Text("Next")
                    }
                }
            }
        }
    }
}