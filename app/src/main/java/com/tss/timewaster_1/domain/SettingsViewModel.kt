package com.tss.timewaster_1.domain

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tss.timewaster_1.data.AppState
import com.tss.timewaster_1.data.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    preferences: Preferences
) : ViewModel() {

    private val preference = preferences
    var state by mutableStateOf(
        SettingsState(
            circleDuration = preferences.getLastDur(),
            createDelay = preferences.getPopSpeed()
        )
    )
        private set

    fun onDurationEnter(duration: String){
        if(duration.toIntOrNull() != null){
            this.state = SettingsState(
                circleDuration = duration.toInt(),
                createDelay = this.state.createDelay
            )
        }
    }

    fun onCreateSpeedEnter(speed: String){
        if(speed.toIntOrNull() != null){
            this.state = SettingsState(
                circleDuration = this.state.circleDuration,
                createDelay = speed.toInt()
            )
        }
    }
    fun onEvent(event: AppEvent) {
        when (event) {
            is AppEvent.SetCircleDuration -> viewModelScope.launch {
                if (event.circleDuration.toIntOrNull() != null) {
                    preference.setLastDuration(event.circleDuration.toInt())
                    Log.d("PREF", preference.getLastDur().toString())
                }
            }

            is AppEvent.SetCreateSpeed -> viewModelScope.launch {
                if (event.createDelay.toIntOrNull() != null) {
                    preference.setPopSpeed(event.createDelay.toInt())
                    Log.d("PREF", preference.getPopSpeed().toString())
                }
            }

            else -> Unit
        }
    }
}