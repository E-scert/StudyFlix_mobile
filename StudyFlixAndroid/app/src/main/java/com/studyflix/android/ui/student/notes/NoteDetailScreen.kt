package com.studyflix.android.ui.student.notes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.studyflix.android.domain.model.StudyNote
import com.studyflix.android.ui.theme.AppColors
import com.studyflix.android.ui.theme.StudentColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    note: StudyNote,
    onBack: () -> Unit
) {

    Scaffold(
        containerColor = AppColors.Background,

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.TopBar
                ),

                title = {
                    Text(
                        text = note.title,
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

        Text(
            text = note.content,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            color = AppColors.TextPrimary
        )
    }
}