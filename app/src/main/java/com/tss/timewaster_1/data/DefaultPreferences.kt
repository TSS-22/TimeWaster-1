package com.tss.timewaster_1.data

import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import com.tss.timewaster_1.domain.Preferences
import com.tss.timewaster_1.domain.UserInfo

class DefaultPreferences(
    private val sharedPref: SharedPreferences
): Preferences {
    override fun setHighScore(score: Int) {
        sharedPref.edit()
            .putInt(Preferences.KEY_HIGH_SCORE, score)
            .apply()
    }

    override fun setPopSpeed(value: Int) {
        sharedPref.edit()
            .putInt(Preferences.KEY_POP_SPEED, value)
            .apply()
    }

    override fun setLastDuration(value: Int) {
        sharedPref.edit()
            .putInt(Preferences.KEY_LAST_DUR, value)
            .apply()
    }

    override fun getHighScore(): Int {
        return sharedPref.getInt(Preferences.KEY_HIGH_SCORE, 0)
    }

    override fun setRadius(value: Float) {
        TODO("Not yet implemented")
    }

    override fun setTickSpeed(value: Int) {
        TODO("Not yet implemented")
    }

    override fun getTickSpeed(): Int {
        return sharedPref.getInt(Preferences.KEY_TICK_SPEED, 100)
    }

    override fun getPopSpeed(): Int {
        return sharedPref.getInt(Preferences.KEY_POP_SPEED, 10)
    }

    override fun getLastDur(): Int {
        return sharedPref.getInt(Preferences.KEY_LAST_DUR, 2500)
    }

    override fun setBallColor(value: Color) {
        TODO("Not yet implemented")
    }

    override fun getBallColor(): Color {
        TODO("Not yet implemented")
    }

    override fun getCircleRadius(): Float {
        return sharedPref.getFloat(Preferences.KEY_RADIUS, 100f)
    }
    override fun getUserInfo(): UserInfo {
        val highScore = sharedPref.getInt(Preferences.KEY_HIGH_SCORE, 0)
        val popSpeed = sharedPref.getInt(Preferences.KEY_POP_SPEED, 1000)
        val lastDur = sharedPref.getInt(Preferences.KEY_LAST_DUR, 1500)

        return UserInfo(
            highScore = highScore,
            popSpeed = popSpeed,
            lastDur = lastDur
        )
    }

}