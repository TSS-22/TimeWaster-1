package com.tss.timewaster_1.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tss.timewaster_1.data.SettingsState
import com.tss.timewaster_1.domain.AppEvent
import com.tss.timewaster_1.domain.Preferences
import com.tss.timewaster_1.domain.SettingsViewModel
import com.tss.timewaster_1.domain.TimeWaster1ViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
//    state: SettingsState,
    preferences: Preferences,
    viewModel: SettingsViewModel
) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "High score: ${preferences.getHighScore()}")
        }
        Spacer(modifier = Modifier.height(40.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Circle generation\nspeed")
                Spacer(modifier = Modifier.weight(1f))
                TextField(
                    value = state.createDelay.toString(),
                    onValueChange = viewModel::onCreateSpeedEnter,
                    modifier = Modifier.weight(0.5f)
                )
                IconButton(onClick = { viewModel.onEvent(AppEvent.SetCreateSpeed(state.createDelay.toString())) }) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "OK")
                }
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Circle duration")
                Spacer(modifier = Modifier.weight(1f))
                TextField(
                    value = state.circleDuration.toString(),
                    onValueChange = viewModel::onDurationEnter,
                    modifier = Modifier.weight(0.5f)
                )
                IconButton(onClick = { viewModel.onEvent(AppEvent.SetCircleDuration(state.circleDuration.toString())) }) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "OK")
                }
            }
        }
    }
}