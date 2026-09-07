package com.studyflix.android.ui.student.assignments

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.studyflix.android.domain.model.AssignmentSubmission
import com.studyflix.android.ui.theme.StudentColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import com.studyflix.android.ui.theme.AppColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import com.studyflix.android.data.preferences.AssignmentTimerDataStore
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import coil.compose.AsyncImage
import java.time.LocalDate
import java.time.ZoneId



@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AssignmentQuestionScreen(
    assignmentId: String,
    onBack: () -> Unit,
    viewModel: AssignmentDetailsViewModel = hiltViewModel()
) {


    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    val timerStore = remember {
        AssignmentTimerDataStore(context)
    }

    val scope = rememberCoroutineScope()

    val answers = rememberSaveable {
        mutableStateMapOf<String, String>()
    }

    var submitted by remember {
        mutableStateOf(false)
    }

    var showSubmitDialog by remember {
        mutableStateOf(false)
    }

    var showExitDialog by remember {
        mutableStateOf(false)
    }

    var submitting by remember {
        mutableStateOf(false)
    }

    var timeLeft by rememberSaveable {
        mutableStateOf(0L)
    }

    var showTimeExpiredDialog by remember {
        mutableStateOf(false)
    }

    var cursorPosition by remember {
        mutableStateOf(0)
    }

    var selectedImage by remember {
        mutableStateOf<String?>(null)
    }

    val assignment = uiState.assignment

    LaunchedEffect(assignmentId) {
        viewModel.loadAssignment(assignmentId)

    }
    LaunchedEffect(assignmentId) {

        FirebaseAuth.getInstance()
            .currentUser
            ?.uid
            ?.let { studentId ->

                viewModel.checkSubmissionStatus(
                    assignmentId,
                    studentId
                )
            }
    }

    LaunchedEffect(assignment) {

        assignment?.questions?.forEach { question ->

            val savedAnswer =
                timerStore.getAnswer(
                    "${assignmentId}_${question.number}"
                )

            if (savedAnswer != null) {

                answers[
                    question.number.toString()
                ] = savedAnswer
            }
        }
    }

    LaunchedEffect(assignment?.duration) {

        if (assignment == null) return@LaunchedEffect

        val savedEndTime =
            timerStore.getEndTime(assignmentId)

        if (savedEndTime != null) {

            val remaining =
                savedEndTime - System.currentTimeMillis()

            timeLeft = remaining.coerceAtLeast(0L)

        } else {

            val durationEndTime =
                System.currentTimeMillis() +
                        (assignment.duration * 60 * 1000L)

            val dueDateEndTime =
                LocalDate.parse(
                    assignment.dueDate
                )
                    .atTime(23, 59, 59)
                    .atZone(
                        ZoneId.systemDefault()
                    )
                    .toInstant()
                    .toEpochMilli()

            val endTime =
                minOf(
                    durationEndTime,
                    dueDateEndTime
                )

            timerStore.saveEndTime(
                assignmentId,
                endTime
            )

            timeLeft =
                assignment.duration * 60 * 1000L
        }
    }

    LaunchedEffect(timeLeft) {

        while (true) {

            val savedEndTime =
                timerStore.getEndTime(assignmentId)
                    ?: break

            val remaining =
                savedEndTime - System.currentTimeMillis()

            timeLeft = remaining.coerceAtLeast(0L)

            if (timeLeft <= 0L) break

            delay(1000)
        }
    }


    LaunchedEffect(timeLeft) {

        if (
            timeLeft == 0L &&
            !submitted &&
            assignment != null
        ) {

            val submission = AssignmentSubmission(
                assignmentId = assignmentId,
                assignmentTitle = assignment.title,

                studentId = FirebaseAuth.getInstance()
                    .currentUser
                    ?.uid
                    .orEmpty(),

                studentName = FirebaseAuth.getInstance()
                    .currentUser
                    ?.displayName
                    ?: "Unknown Student",

                startedAt = System.currentTimeMillis(),
                submittedAt = System.currentTimeMillis(),

                answers = answers.toMap(),

                isMarked = false,
                score = 0,
                feedback = ""
            )

            submitting = true

            viewModel.submitAssignment(
                submission
            )

            timerStore.clearEndTime(
                assignmentId
            )

            assignment.questions.forEach { question ->

                timerStore.clearAnswer(
                    "${assignmentId}_${question.number}"
                )
            }

            submitted = true
            submitting = false

            showTimeExpiredDialog = true
        }
    }

    BackHandler(
        enabled = !submitted
    ) {
        showExitDialog = true
    }

    val hours = timeLeft / 1000 / 60 / 60
    val minutes = (timeLeft / 1000 / 60) % 60
    val seconds = (timeLeft / 1000) % 60
    val isWarning = timeLeft <= 5 * 60 * 1000L

    val formattedTime =
        String.format(
            "%02d:%02d:%02d",
            hours,
            minutes,
            seconds
        )


    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.TopBar
                ),
                title = {
                    Text(
                        "Assignment Questions",
                        color = StudentColors.Primary
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (submitted) {
                                onBack()
                            } else {
                                showExitDialog = true
                            }
                        }
                    ){
                        Icon(
                            Icons.Default.ArrowBack,
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
                .padding(16.dp)
        ) {

            Text(
                text = assignment?.title ?: "Loading...",
                style = MaterialTheme.typography.headlineSmall,
                color = StudentColors.Primary
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "${assignment?.questions?.size ?: 0} Questions",
                style = MaterialTheme.typography.bodyMedium,
                color = StudentColors.Primary
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "⏰ Time Remaining: $formattedTime",
                color = if (isWarning) Color.Red else StudentColors.Primary,
                style = MaterialTheme.typography.titleMedium
            )
            if (isWarning) {

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "⚠️ Less than 5 minutes remaining!",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.Card
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Assessment Summary",
                        color = StudentColors.Primary,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Total Questions: ${assignment?.questions?.size ?: 0}",
                        color = Color.White
                    )
                    Text(
                        text = "Status: Active ",
                        color = Color.White
                    )

                    Text(
                        text = "Answered: ${
                            answers.count { it.value.isNotBlank() }
                        } / ${assignment?.questions?.size ?: 0}",
                        color = Color.White
                    )

                    Text(
                        text = "Total Marks: ${assignment?.totalMarks ?: 0}",
                        color = Color.White
                    )

                    Text(
                        text = "Duration: ${assignment?.examTime ?: ""}",
                        color = Color.White
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                items(
                    assignment?.questions ?: emptyList()
                ) { question ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = AppColors.Card
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = "Question ${question.number}",
                                color = StudentColors.Primary,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Text(
                                text = "Marks: ${question.marks}",
                                color = StudentColors.Primary
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = question.text,
                                color = Color.White
                            )
                            if (question.imageUrl.isNotBlank()) {

                                Spacer(
                                    modifier = Modifier.height(12.dp)
                                )

                                AsyncImage(
                                    model = question.imageUrl,
                                    contentDescription = "Question Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedImage = question.imageUrl
                                        }
                                )
                            }

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(
                                        rememberScrollState()
                                    )
                            ) {

                                listOf("+", "-", "×", "÷", "=", "π", "θ", "√", "∛", "∞", "≠", "≤", "≥", "±", "%", "²", "³", "^", "_", "(", ")", "[", "]", "sin(", "cos(", "tan(", "log(", "ln(", "∫", "∂"
                                ).forEach { symbol ->

                                    OutlinedButton(
                                        onClick = {

                                            val current =
                                                answers[question.number.toString()]
                                                    ?: ""

                                            val text =
                                                answers[question.number.toString()]
                                                    ?: ""

                                            val newText =
                                                text.substring(
                                                    0,
                                                    cursorPosition
                                                ) +
                                                        symbol +
                                                        text.substring(
                                                            cursorPosition
                                                        )

                                            answers[
                                                question.number.toString()
                                            ] = newText

                                            cursorPosition += symbol.length
                                        },
                                        contentPadding = PaddingValues(
                                            horizontal = 8.dp,
                                            vertical = 2.dp
                                        )
                                    ) {
                                        Text(symbol)
                                    }
                                }
                            }

                            OutlinedTextField(
                                value = TextFieldValue(
                                    text = answers[question.number.toString()] ?: "",
                                    selection = TextRange(
                                        cursorPosition
                                    )
                                ),
                                onValueChange = { value ->
                                    cursorPosition =
                                        value.selection.start

                                    answers[
                                        question.number.toString()
                                    ] = value.text

                                    answers[question.number.toString()] = value.text

                                    scope.launch {
                                        timerStore.saveAnswer(
                                            "${assignmentId}_${question.number}",
                                            value.text
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                label = {
                                    Text("Your Answer",color = androidx.compose.ui.graphics.Color.White)
                                }
                            )
                        }
                    }
                }
            }

            if (submitting) {

                Text(
                    text = "Submitting assignment...",
                    color = StudentColors.Primary
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }

            if (submitted) {

                Text(
                    text = "✅ Assignment submitted",
                    color = StudentColors.Primary
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.Card
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Button(
                        enabled = !submitted && !submitting,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = StudentColors.Primary
                        ),
                        onClick = {
                            showSubmitDialog = true

                        }

                    ) {
                        Text(
                            text = "Submit Assignment",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    if (showSubmitDialog) {

                        AlertDialog(
                            onDismissRequest = {
                                showSubmitDialog = false
                            },

                            title = {
                                Text("Submit Assignment")
                            },

                            text = {
                                Text(
                                    "Are you sure you want to submit your assignment? You will not be able to edit it afterwards."
                                )
                            },

                            confirmButton = {
                                Button(
                                    onClick = {

                                        showSubmitDialog = false
                                        val submission = AssignmentSubmission(
                                assignmentId = assignmentId,
                                assignmentTitle = assignment?.title ?: "",


                                studentId = FirebaseAuth.getInstance()
                                    .currentUser
                                    ?.uid
                                    .orEmpty(),

                                studentName = FirebaseAuth.getInstance()
                                    .currentUser
                                    ?.displayName
                                    ?: "Unknown Student",
                                startedAt = System.currentTimeMillis(),
                                submittedAt = System.currentTimeMillis(),

                                answers = answers.toMap(),

                                isMarked = false,
                                score = 0,
                                feedback = ""
                            )

                            submitting = true

                                        viewModel.submitAssignment(
                                            submission
                                        )

                                        scope.launch {

                                            timerStore.clearEndTime(
                                                assignmentId
                                            )

                                            assignment?.questions?.forEach { question ->

                                                timerStore.clearAnswer(
                                                    "${assignmentId}_${question.number}"
                                                )
                                            }
                                        }

                                        submitted = true
                                        submitting = false
                                    }
                                ) {
                                    Text("Submit")
                                }
                            },

                            dismissButton = {
                                Button(
                                    onClick = {
                                        showSubmitDialog = false
                                    }
                                ) {
                                    Text("Cancel")
                                }
                            }
                        )
                    }

                    if (showTimeExpiredDialog) {

                        AlertDialog(
                            onDismissRequest = {},

                            title = {
                                Text("Time Expired")
                            },

                            text = {
                                Text(
                                    "Time has expired. Your assignment was submitted automatically."
                                )
                            },

                            confirmButton = {
                                Button(
                                    onClick = {
                                        showTimeExpiredDialog = false
                                    }
                                ) {
                                    Text("OK")
                                }
                            }
                        )
                    }
                    if (showExitDialog) {

                        AlertDialog(
                            onDismissRequest = {
                                showExitDialog = false
                            },

                            title = {
                                Text("Leave Assignment?")
                            },

                            text = {
                                Text(
                                    "Your timer will continue running even if you leave this screen."
                                )
                            },

                            confirmButton = {
                                Button(
                                    onClick = {
                                        showExitDialog = false
                                        onBack()
                                    }
                                ) {
                                    Text("Leave")
                                }
                            },

                            dismissButton = {
                                Button(
                                    onClick = {
                                        showExitDialog = false
                                    }
                                ) {
                                    Text("Stay")
                                }
                            }
                        )
                    }

                    if (selectedImage != null) {

                        Dialog(
                            onDismissRequest = {
                                selectedImage = null
                            }
                        ) {

                            AsyncImage(
                                model = selectedImage,
                                contentDescription = "Fullscreen Image",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }


                }
            }
        }
    }
}