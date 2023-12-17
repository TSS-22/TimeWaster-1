package com.tss.timewaster_1.domain

import androidx.compose.ui.graphics.Color

interface Preferences {

    fun setHighScore(score: Int)
    fun setPopSpeed(value: Int)
    fun setLastDuration(value: Int)
    fun setBallColor(value: Color)
    fun setRadius(value: Float)
    fun setTickSpeed(value: Int)

    fun getUserInfo(): UserInfo
    fun getHighScore(): Int
    fun getPopSpeed(): Int
    fun getLastDur(): Int
    fun getBallColor(): Color
    fun getCircleRadius(): Float
    fun getTickSpeed(): Int

    companion object{
        const val KEY_HIGH_SCORE = "high_score"
        const val KEY_POP_SPEED = "pop_speed"
        const val KEY_LAST_DUR = "last_dur"
        const val KEY_RADIUS = "radius"
        const val KEY_TICK_SPEED = "tick_speed"
        const val KEY_BALL_COLOR = "ball_color"
    }
}