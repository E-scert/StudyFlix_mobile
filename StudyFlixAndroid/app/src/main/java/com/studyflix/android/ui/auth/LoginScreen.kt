package com.studyflix.android.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.studyflix.android.core.ui.theme.StudyFlixBackground
import com.studyflix.android.domain.model.UserRole

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    portal: String,
    onSignedIn: (UserRole) -> Unit,
    onNavigateToSignUp: () -> Unit,
    onBack: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.signedInRole) {

        uiState.signedInRole?.let { role ->

            if (roleMatchesPortal(role, portal)) {

                onSignedIn(role)

            } else {

                val correctPortal = when (role) {
                    UserRole.STUDENT -> "Student Portal"
                    UserRole.TEACHER -> "Teacher Portal"
                    UserRole.ADMIN -> "Admin Portal"
                }

                viewModel.showPortalError(
                    "This account belongs to the $correctPortal."
                )

                viewModel.consumeSignedInEvent()
            }
        }
    }

    Scaffold(
        containerColor = StudyFlixBackground
    )  { padding: PaddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("← Back")
            }
            val portalIcon = when (portal) {
                "student" -> Icons.Default.School
                "teacher" -> Icons.Default.Person
                "admin" -> Icons.Default.AdminPanelSettings
                else -> Icons.Default.School
            }

            val portalColor = when (portal) {
                "student" -> Color(0xFF00FFCC)
                "teacher" -> Color(0xFFFFD700)
                "admin" -> Color(0xFFE8003D)
                else -> Color(0xFF00FFCC)
            }

            Surface(
                modifier = Modifier.size(100.dp),
                shape = RoundedCornerShape(50.dp),
                color = portalColor.copy(alpha = 0.12f)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = portalIcon,
                        contentDescription = null,
                        tint = portalColor,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            val portalTitle = when (portal) {
                "student" -> "Student Portal"
                "teacher" -> "Teacher Portal"
                "admin" -> "Admin Portal"
                else -> "StudyFlix"
            }

            val portalSubtitle = when (portal) {
                "student" -> "Sign in to continue learning"
                "teacher" -> "Manage classes and assessments"
                "admin" -> "Manage users and platform settings"
                else -> "Welcome back"
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(3.dp)
                    .background(
                        portalColor,
                        RoundedCornerShape(50)
                    )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = portalTitle,
                style = MaterialTheme.typography.headlineSmall,
                color = portalColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = portalSubtitle,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = portalColor,
                    unfocusedBorderColor = portalColor.copy(alpha = 0.6f),

                    focusedLabelColor = portalColor,
                    unfocusedLabelColor = portalColor.copy(alpha = 0.7f),

                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,

                    cursorColor = portalColor
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = portalColor,
                    unfocusedBorderColor = portalColor.copy(alpha = 0.6f),

                    focusedLabelColor = portalColor,
                    unfocusedLabelColor = portalColor.copy(alpha = 0.7f),

                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,

                    cursorColor = portalColor
                ),
                modifier = Modifier.fillMaxWidth()
            )

            uiState.errorMessage?.let { message ->

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = viewModel::signIn,
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),

                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = portalColor,
                    contentColor = Color.Black
                )
            ) {

                if (uiState.isLoading) {

                    CircularProgressIndicator(
                        modifier = Modifier.height(20.dp)
                    )

                } else {

                    Text("Sign In")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (portal == "student") {

                TextButton(
                    onClick = onNavigateToSignUp
                ) {
                    Text("New student? Create an account")
                }

            } else if (portal == "teacher") {

                Text(
                    text = "Teacher accounts are created by an administrator.",
                    style = MaterialTheme.typography.bodySmall,
                    color = portalColor
                )

            } else {

                Text(
                    text = "Administrator accounts are managed internally.",
                    style = MaterialTheme.typography.bodySmall,
                    color = portalColor
                )
            }
        }
    }
}
private fun roleMatchesPortal(
    role: UserRole,
    portal: String
): Boolean {

    return when (portal) {
        "student" -> role == UserRole.STUDENT
        "teacher" -> role == UserRole.TEACHER
        "admin" -> role == UserRole.ADMIN
        else -> false
    }
}