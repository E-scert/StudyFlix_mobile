package com.studyflix.android.ui.student.marks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.studyflix.android.domain.model.Mark

/** Equivalent of public/student/marks/results view: summary average + a scrollable list. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarksScreen(onBack: () -> Unit, viewModel: MarksViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = StudyFlixBackground,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0F172A)
                ),
                title = { Text("Marks & Results", color = Color(0xFF00FFCC)) },
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

        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(
                    text = "Average: ${uiState.averagePercentage}%",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF00FFCC)
                )
            }
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.marks, key = Mark::id) { mark ->
                    Card(modifier = Modifier.fillMaxWidth(),shape = RoundedCornerShape(20.dp)) {
                        ListItem(
                            headlineContent = { Text(mark.name) },
                            supportingContent = {
                                Text(
                                    "${mark.score}/${mark.total} marks • ${mark.dateIso}"
                                )
                            },
                            trailingContent = {
                                Text(
                                    text = "${mark.percentage}%",
                                    color = Color(0xFF00FFCC),
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
