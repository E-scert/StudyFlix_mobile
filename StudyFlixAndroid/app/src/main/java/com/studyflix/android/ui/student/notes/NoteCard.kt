package com.studyflix.android.ui.student.notes


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.studyflix.android.domain.model.StudyNote
import com.studyflix.android.ui.theme.AppColors
import com.studyflix.android.ui.theme.StudentColors

@Composable
fun NoteCard(
    note: StudyNote,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),

        colors = CardDefaults.cardColors(
            containerColor = AppColors.Card
        )
    ) {

        ListItem(
            headlineContent = {
                Text(
                    note.title
                )
            },

            supportingContent = {
                Text(
                    note.topic
                )
            },

            trailingContent = {
                Text(
                    "›",
                    color = StudentColors.Primary,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        )
    }
}