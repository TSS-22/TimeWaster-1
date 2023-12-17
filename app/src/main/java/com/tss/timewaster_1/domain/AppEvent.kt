package com.tss.timewaster_1.domain

import androidx.compose.ui.geometry.Offset

sealed class AppEvent{
    object ClickStart: AppEvent()
    data class ClickCircle(val clickPosition: Offset): AppEvent()
    data class SetCreateSpeed(val createDelay: String): AppEvent()
    data class SetCircleDuration(val circleDuration: String): AppEvent()
}
