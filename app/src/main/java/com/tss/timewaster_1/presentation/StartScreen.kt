package com.tss.timewaster_1.presentation


import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import com.tss.timewaster_1.data.AppState
import com.tss.timewaster_1.domain.AppEvent
import com.tss.timewaster_1.domain.Preferences
import com.tss.timewaster_1.domain.TimeWaster1ViewModel

@Composable
fun StartScreen(
    state: AppState,
    preferences: Preferences,
    viewModel: TimeWaster1ViewModel
) {
    viewModel.setCircleParameters()
    var notYetCalled = true

    if (state.gameRunning) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(true){
                        detectTapGestures {
                            viewModel.onEvent(AppEvent.ClickCircle(it))
                        }
                    }
            ){
                for(circle in state.circles){
                    drawCircle(
                        color = circle.color,
                        radius = preferences.getCircleRadius(),
                        center = circle.position
                    )
                }
            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    viewModel.onEvent(AppEvent.ClickStart)
                }
                .onGloballyPositioned {
                    if (notYetCalled) {
                        viewModel.setCanvasConstraints(it.size)
                        notYetCalled = false
                    }
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Touch screen to start",
                style = MaterialTheme.typography.headlineLarge,
            )
        }
    }
}
