package com.studyflix.android.ui.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Scaffold for the admin portal root, same pattern note as
 * TeacherDashboardScreen: wire an AdminDashboardViewModel here backed by
 * use cases like GetSchoolsUseCase / ApproveContentUseCase /
 * GetPlatformStatsUseCase over an AdminRepository (school management,
 * content approval, user management -- see PROJECT_DOCUMENTATION.md
 * section 5.3).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Dashboard") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Filled.Logout, contentDescription = "Log out")
                    }
                }
            )
        }
    ) { padding: PaddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "School management, teacher approval, content moderation " +
                    "and platform settings modules plug in here.",
                style = MaterialTheme.typography.bodyMedium
            )
            ListItem(headlineContent = { Text("Schools") })
            ListItem(headlineContent = { Text("Teacher Approvals") })
            ListItem(headlineContent = { Text("Content Moderation") })
            ListItem(headlineContent = { Text("Platform Settings") })
        }
    }
}
