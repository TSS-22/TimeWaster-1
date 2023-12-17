package com.tss.timewaster_1.presentation.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import com.tss.timewaster_1.presentation.navigation.AppScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeWasterAppBar(
    score: Int,
    onClickSetting: () -> Unit,
    currentScreen: AppScreen,
    navigateUp: () -> Unit,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        title = { Text(text = "Time Waster 1") },
        navigationIcon = {
            if (currentScreen == AppScreen.Settings) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            } else {
                Text(
                    text = score.toString(),
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        },
        actions = {
            if (currentScreen != AppScreen.Settings) {
                IconButton(
                    onClick = onClickSetting
                )
                {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings"
                    )
                }
            }
        },
    )
}
