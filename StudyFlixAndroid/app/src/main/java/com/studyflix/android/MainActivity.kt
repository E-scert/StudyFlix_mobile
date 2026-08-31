package com.studyflix.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.studyflix.android.core.navigation.StudyFlixNavGraph
import com.studyflix.android.core.ui.theme.StudyFlixTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyFlixTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    StudyFlixNavGraph()
                }
            }
        }
    }
}
