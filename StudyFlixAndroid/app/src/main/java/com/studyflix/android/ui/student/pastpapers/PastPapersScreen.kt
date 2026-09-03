package com.studyflix.android.ui.student.pastpapers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton

data class PastPaperUi(
    val title: String,
    val subject: String,
    val year: Int,
    val term: String,

)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PastPapersScreen(
    onBack: () -> Unit,
    viewModel: PastPapersViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var selectedSubject by remember { mutableStateOf("All Subjects") }
    val years = uiState.papers.map { it.year.toString() }.distinct().sortedDescending()
    var expanded by remember { mutableStateOf(false) }
    var selectedYear by remember {
        mutableStateOf("All Years")
    }


    var yearExpanded by remember {
        mutableStateOf(false)
    }

    val filteredPapers = uiState.papers.filter { paper ->

        val subjectMatches =
            selectedSubject == "All Subjects" ||
                    paper.subject == selectedSubject

        val yearMatches =
            selectedYear == "All Years" ||
                    paper.year.toString() == selectedYear

        subjectMatches && yearMatches
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
                        "Past Papers",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
Box {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            expanded = true
        }
    ) {
        Text(
            text = selectedSubject,
            color = StudentColors.Primary
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            expanded = false
        },
        containerColor = AppColors.Card
    )
    {

        DropdownMenuItem(
            text = {
                Text(
                    text = "All Subjects",
                    color = StudentColors.Primary
                )
            },
            onClick = {
                selectedSubject = "All Subjects"
                expanded = false
            }
        )

        DropdownMenuItem(
            text = { Text("Mathematics", color = StudentColors.Primary) },
            onClick = {
                selectedSubject = "Mathematics"
                expanded = false
            }
        )

        DropdownMenuItem(
            text = { Text("Natural Science", color = StudentColors.Primary) },
            onClick = {
                selectedSubject = "Natural Science"
                expanded = false
            }
        )
    }
}

            Box {

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        yearExpanded = true
                    }
                ) {
                    Text(
                        text = selectedYear,
                        color = StudentColors.Primary
                    )
                }

                DropdownMenu(
                    expanded = yearExpanded,
                    onDismissRequest = {
                        yearExpanded = false
                    },
                    containerColor = AppColors.Card
                ) {

                    DropdownMenuItem(
                        text = {
                            Text(
                                "All Years",
                                color = StudentColors.Primary
                            )
                        },
                        onClick = {
                            selectedYear = "All Years"
                            yearExpanded = false
                        }
                    )

                    years.forEach { year ->

                        DropdownMenuItem(
                            text = {
                                Text(
                                    year,
                                    color = StudentColors.Primary
                                )
                            },
                            onClick = {
                                selectedYear = year
                                yearExpanded = false
                            }
                        )
                    }
                }
            }
            Text(
                text = "${filteredPapers.size} papers found",
                color = StudentColors.Primary,
                style = MaterialTheme.typography.bodyMedium
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

                if (filteredPapers.isEmpty()) {

                    item {

                        Text(
                            text = "📄 No papers found",
                            color = StudentColors.Primary,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "Try changing your filters",
                            color = Color.White
                        )
                    }
                }

                items(filteredPapers) { paper ->

                    androidx.compose.material3.Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {

                        Text(
                            text = paper.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = StudentColors.Primary
                        )
                            val isMemo =
                                paper.title.contains(
                                    "memorandum",
                                    ignoreCase = true
                                )

                            Text(
                                text =
                                    if (isMemo)
                                        "📘 MEMORANDUM"
                                    else
                                        "📄 QUESTION PAPER",

                                color =
                                    if (isMemo)
                                        Color(0xFF0E7E67)
                                    else
                                        Color(0xFF3AB641),

                                style = MaterialTheme.typography.labelLarge
                            )
                            Text(
                                text = "✅ Available",
                                color = Color.Green,
                                style = MaterialTheme.typography.labelMedium
                            )
                            Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                            Text(
                                text = paper.subject,
                                style = MaterialTheme.typography.bodyLarge,
                                color = StudentColors.Primary
                            )

                            Text(
                                text = "${paper.year} • ${paper.term}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = StudentColors.Primary
                            )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {

                                    android.util.Log.d(
                                        "PAPER_URL",
                                        paper.fileUrl
                                    )

                                    if (paper.fileUrl.isNotBlank()) {

                                        val intent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(paper.fileUrl)
                                        )

                                        context.startActivity(intent)
                                    }
                                }
                            ) {
                                Text("View Paper")
                            }
                    }
                }
            }
        }

        }

    }
}