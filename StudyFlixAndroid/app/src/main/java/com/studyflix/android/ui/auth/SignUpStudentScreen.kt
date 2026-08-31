package com.studyflix.android.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

/** Mirrors the student sign-up modal fields from public/student/js/auth.js (showAuthModal). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpStudentScreen(
    onSignedUp: () -> Unit,
    onBack: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.signedUpStudent) {
        if (uiState.signedUpStudent != null) onSignedUp()
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Create Student Account") }) }) { padding: PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = uiState.firstName,
                onValueChange = { value -> viewModel.onFieldChange { copy(firstName = value) } },
                label = { Text("First name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.surname,
                onValueChange = { value -> viewModel.onFieldChange { copy(surname = value) } },
                label = { Text("Surname") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { value -> viewModel.onFieldChange { copy(email = value) } },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { value -> viewModel.onFieldChange { copy(password = value) } },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.school,
                onValueChange = { value -> viewModel.onFieldChange { copy(school = value) } },
                label = { Text("School") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.grade,
                onValueChange = { value -> viewModel.onFieldChange { copy(grade = value) } },
                label = { Text("Grade (e.g. Grade 8)") },
                modifier = Modifier.fillMaxWidth()
            )

            uiState.errorMessage?.let { message ->
                Text(text = message, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(8.dp))
            Button(
                onClick = viewModel::signUp,
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.height(20.dp))
                } else {
                    Text("Create Account")
                }
            }
            TextButton(onClick = onBack) { Text("Back to sign in") }

            // New accounts start in "pending" status, matching StudentAuth.loadUserData()
            // on web -- the account requires admin approval before full access is granted.
            Text(
                text = "Your account will need admin approval before you can access all features.",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
