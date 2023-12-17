package com.tss.timewaster_1.data


data class AppState(
    val currentScore: Int = 0,
    val gameRunning: Boolean = false,
    val circles: List<Circle> = mutableListOf()
)
