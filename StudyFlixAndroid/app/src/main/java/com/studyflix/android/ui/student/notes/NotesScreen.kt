package com.studyflix.android.ui.student.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.studyflix.android.ui.theme.AppColors
import com.studyflix.android.ui.theme.StudentColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.weight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    onBack: () -> Unit,
    onOpenNote: (String) -> Unit,
    viewModel: NotesViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.TopBar
                ),

                title = {
                    Text(
                        text = "Study Notes",
                        color = StudentColors.Primary
                    )
                },

                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                label = {
                    Text("Search Notes")
                },
                modifier = Modifier.fillMaxWidth()
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                item {

                    FilterChip(
                        selected = uiState.selectedSection == "all",
                        onClick = {
                            viewModel.selectSection("all")
                        },
                        label = {
                            Text("All Notes")
                        }
                    )
                }

                item {

                    FilterChip(
                        selected = uiState.selectedSection == "class",
                        onClick = {
                            viewModel.selectSection("class")
                        },
                        label = {
                            Text("My Class Notes")
                        }
                    )
                }

                item {

                    FilterChip(
                        selected = uiState.selectedSection == "other",
                        onClick = {
                            viewModel.selectSection("other")
                        },
                        label = {
                            Text("Other Notes")
                        }
                    )
                }
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                item {
                    FilterChip(
                        selected = uiState.selectedSubject == "all",
                        onClick = {
                            viewModel.selectSubject("all")
                        },
                        label = {
                            Text("All Subjects")
                        }
                    )
                }

                item {
                    FilterChip(
                        selected = uiState.selectedSubject == "Mathematics",
                        onClick = {
                            viewModel.selectSubject("Mathematics")
                        },
                        label = {
                            Text("Mathematics")
                        }
                    )
                }

                item {
                    FilterChip(
                        selected = uiState.selectedSubject == "Natural Science",
                        onClick = {
                            viewModel.selectSubject("Natural Science")
                        },
                        label = {
                            Text("Natural Science")
                        }
                    )
                }
            }

            Text(
                text = "Notes Loaded: ${uiState.notes.size}",
                color = StudentColors.Primary
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(

                        uiState.notes.filter { note ->

                            val matchesSearch =

                                note.title.contains(
                                    uiState.searchQuery,
                                    ignoreCase = true
                                ) ||

                                        note.topic.contains(
                                            uiState.searchQuery,
                                            ignoreCase = true
                                        ) ||

                                        note.subject.contains(
                                            uiState.searchQuery,
                                            ignoreCase = true
                                        )

                            val matchesSection = when (uiState.selectedSection) {

                                "all" -> true

                                "class" -> note.uploadedBy != null

                                "other" -> note.uploadedBy == null

                                else -> true
                            }

                            val matchesSubject = when (uiState.selectedSubject) {

                                "all" -> true

                                else -> note.subject == uiState.selectedSubject
                            }

                            matchesSearch &&
                                    matchesSection &&
                                    matchesSubject
                        }

                    ) { note ->

                        NoteCard(
                            note = note,
                            onClick = {
                                onOpenNote(note.id)
                            }
                        )
                    }
                }
            }

        }
    }
}