package com.studyflix.android.ui.student.videos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.studyflix.android.core.ui.theme.StudyFlixBackground
import com.studyflix.android.domain.model.VideoContent

/** Equivalent of public/student/videos.html + js/videos.js: season tabs + episode list. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideosScreen(onBack: () -> Unit, viewModel: VideosViewModel = hiltViewModel()) {
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
                        "Learning Videos",
                        color = Color(0xFF7C4DFF)
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
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(24.dp))
                return@Column
            }

            uiState.errorMessage?.let {
                Text(
                    text = "Showing cached videos ($it)",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.seasons) { season ->
                    FilterChip(
                        selected = season == uiState.selectedSeason,
                        onClick = { viewModel.selectSeason(season) },
                        label = { Text("Season $season") }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.visibleVideos, key = VideoContent::id) { video ->
                    VideoRow(video)
                }
            }
        }
    }
}

@Composable
private fun VideoRow(video: VideoContent) {
    Card(modifier = Modifier.fillMaxWidth()) {
        ListItem(
            headlineContent = { Text(video.title) },
            supportingContent = { Text("${video.subject} • ${video.duration}") },
            leadingContent = {
                Icon(
                    imageVector = if (video.locked)
                        Icons.Filled.Lock
                    else
                        Icons.Filled.PlayCircle,

                    contentDescription = null,

                    tint = if (video.locked)
                        androidx.compose.ui.graphics.Color.Red
                    else
                        androidx.compose.ui.graphics.Color(0xFF00FFCC)
                )

            }
        )
    }
}
