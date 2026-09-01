package com.studyflix.android.ui.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.studyflix.android.core.ui.theme.StudyFlixBackground
import com.studyflix.android.core.ui.theme.StudyFlixMuted
import com.studyflix.android.core.ui.theme.StudyFlixPrimary

@Composable
fun LandingScreen(
    onStudentClick: () -> Unit,
    onTeacherClick: () -> Unit,
    onAdminClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        StudyFlixBackground,
                        Color(0xFF121A2D),
                        Color(0xFF0A0E1A)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "STUDYFLIX",
                style = MaterialTheme.typography.headlineLarge,
                color = StudyFlixPrimary,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Learn. Challenge. Excel.",
                color = StudyFlixMuted,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Choose your portal to continue",
                color = StudyFlixMuted
            )

            Spacer(modifier = Modifier.height(42.dp))

            RoleCard(
                title = "Student Portal",
                description = "Access videos, quizzes, marks and learning resources.",
                icon = Icons.Default.School,
                iconColor = StudyFlixPrimary,
                onClick = onStudentClick
            )

            Spacer(modifier = Modifier.height(18.dp))

            RoleCard(
                title = "Teacher Portal",
                description = "Manage students, assignments and learning content.",
                icon = Icons.Default.Person,
                iconColor = Color(0xFFFFD700),
                onClick = onTeacherClick
            )

            Spacer(modifier = Modifier.height(18.dp))

            RoleCard(
                title = "Admin Portal",
                description = "Control schools, users and platform settings.",
                icon = Icons.Default.AdminPanelSettings,
                iconColor = Color(0xFFE8003D),
                onClick = onAdminClick
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Powered by StudyFlix",
                color = StudyFlixMuted
            )
        }
    }
}

@Composable
private fun RoleCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable {
                onClick()
            },

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

        shape = RoundedCornerShape(20.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = 1.dp,
                        color = iconColor.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(16.dp)
                    ),

                color = iconColor.copy(alpha = 0.1f)
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconColor,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(18.dp))

            Column {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = description,
                    color = StudyFlixMuted
                )
            }
        }
    }
}