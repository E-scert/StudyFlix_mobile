package com.studyflix.android.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = StudyFlixPrimary,
    secondary = StudyFlixSecondary,
    background = StudyFlixBackground,
    surface = StudyFlixSurface,
    error = StudyFlixError,
    onPrimary = StudyFlixBackground,
    onBackground = StudyFlixOnDark,
    onSurface = StudyFlixOnDark
)

private val LightColors = lightColorScheme(
    primary = StudyFlixSecondary,
    secondary = StudyFlixPrimary,
    error = StudyFlixError
)

@Composable
fun StudyFlixTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = StudyFlixTypography,
        content = content
    )
}
