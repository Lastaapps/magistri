package cz.lastaapps.ui.common.locals

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf

val LocalSnackBarState = compositionLocalOf { SnackbarHostState() }
