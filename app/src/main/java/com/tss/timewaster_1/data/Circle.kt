package com.tss.timewaster_1.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class Circle(
    val position: Offset,
    val timeLeft: Int,
    val color: Color
)
