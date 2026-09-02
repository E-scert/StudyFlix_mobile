package com.studyflix.android.ui.student.quizzes

import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment

/** Equivalent of public/student/take-quiz.html: one question at a time, prev/next, submit. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TakeQuizScreen(
    onFinished: () -> Unit,
    viewModel: TakeQuizViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

//    LaunchedEffect(uiState.finalScore) {
//        if (uiState.finalScore != null) onFinished()
//    }

    val quiz = uiState.quiz

    if (uiState.finalScore != null && quiz != null) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.Background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
             {

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.Card
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = quiz.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = StudentColors.Primary
                    )


                    Text(
                        text = "Subject: ${quiz.subject}"
                        ,color = Color.White
                    )

                    Text(
                        text = "Questions: ${quiz.questions.size}"
                        ,color = Color.White
                    )

                    Text(
                        text = "Time Limit: ${quiz.timeLimitMinutes} mins"
                        ,color = Color.White
                    )

                    if (uiState.timedOut) {

                        Text(
                            text = "⏰ Time Expired",
                            color = AppColors.Error
                        )

                        Text(
                            text = "Your quiz was submitted automatically."
                            ,color = Color.White
                        )
                    }

                    val score = uiState.finalScore ?: 0

                    val percentage =
                        if (quiz.totalMarks > 0)
                            (score * 100) / quiz.totalMarks
                        else
                            0

                    val passed = percentage >= 50

                    val correctAnswers =
                        quiz.questions.mapIndexed { index, question ->

                            if (
                                uiState.answers.getOrNull(index) ==
                                question.correctIndex
                            ) 1 else 0

                        }.sum()

                    val wrongAnswers =
                        quiz.questions.size - correctAnswers



                    Text(
                        text = "Score: $score / ${quiz.totalMarks}"
                        ,color = Color.White
                    )

                    Text(
                        text = "$percentage%",
                        style = MaterialTheme.typography.displayLarge,
                        color = StudentColors.Primary
                    )

                    Text(
                        text = "Correct Answers: $correctAnswers"
                        ,color = Color.White
                    )

                    Text(
                        text = "Wrong Answers: $wrongAnswers"
                        ,color = Color.White
                    )

                    Text(
                        text = if (passed) "PASS ✅" else "FAIL ❌",
                        style = MaterialTheme.typography.headlineLarge,
                        color = if (passed) Color.Green else Color.Red
                    )
                    Button(
                        onClick = onFinished
                    ) {
                        Text("Back to Quizzes")
                    }
                }
            }
        }
        return
    }


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
            val minutes = uiState.timeRemainingSeconds / 60
            val seconds = uiState.timeRemainingSeconds % 60

            Text(
                text = String.format(
                    "Time Remaining: %02d:%02d", minutes, seconds),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Yellow
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