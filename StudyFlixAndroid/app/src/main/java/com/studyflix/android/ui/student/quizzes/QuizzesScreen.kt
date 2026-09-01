package com.studyflix.android.ui.student.quizzes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.studyflix.android.core.ui.theme.StudyFlixBackground
import com.studyflix.android.domain.model.Quiz

/** Equivalent of public/student/quizzes.html: list of published quizzes. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizzesScreen(
    onBack: () -> Unit,
    onOpenQuiz: (String) -> Unit,
    viewModel: QuizzesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = StudyFlixBackground,
        topBar = {

            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0F172A)
                ),
                title = {
                    Text(
                        "Practice Quizzes",
                        color = Color(0xFF00FFCC)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF7C4DFF)
                    ) }
                }
            )
        }
    ) { padding: PaddingValues ->
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(padding).padding(24.dp))
            return@Scaffold
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.quizzes, key = Quiz::id) { quiz ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    onClick = { onOpenQuiz(quiz.id) }
                )
                 {
                    ListItem(
                        headlineContent = {
                            Text(quiz.title)
                        },

                        supportingContent = {
                            Text(
                                "${quiz.subject} • ${quiz.questions.size} questions • ${quiz.totalMarks} marks"
                            )
                        },

                        leadingContent = {
                            Icon(
                                imageVector = Icons.Default.Quiz,
                                contentDescription = null,
                                tint = Color(0xFF00FFCC)
                            )
                        },
                        trailingContent = {
                            Text(
                                text = "›",
                                color = Color(0xFF00FFCC),
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    )
                }
            }
        }
    }
}
