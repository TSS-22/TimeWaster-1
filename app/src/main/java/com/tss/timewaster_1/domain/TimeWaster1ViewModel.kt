package com.tss.timewaster_1.domain

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tss.timewaster_1.data.AppState
import com.tss.timewaster_1.data.Circle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

@HiltViewModel
class TimeWaster1ViewModel @Inject constructor(
    preferences: Preferences,
) : ViewModel() {

    private val preference = preferences
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0

    private var baseColor: Color = Color(71f/255,255f/255,67f/255)

    private var canvasWidth: Int = 0
    private var canvasHeight: Int = 0

    private var circleDuration: Int = preferences.getLastDur()
    private var circleApparition: Int = preferences.getPopSpeed()
    private var circleRadius: Float = preferences.getCircleRadius()
    private var tickSpeed: Int = preferences.getTickSpeed()

    fun setCircleParameters(){
        circleDuration = preference.getLastDur()
        circleApparition = preference.getPopSpeed()
        circleRadius = preference.getCircleRadius()
        tickSpeed = preference.getTickSpeed()
    }

    fun setScreenConstraints(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
    }

    fun setCanvasConstraints(dimension: IntSize){
        canvasWidth = dimension.width
        canvasHeight = dimension.height
    }

    var state = MutableStateFlow(AppState())
        private set

    init {
        initState()
    }

    private fun initState() {
        state.update {
            AppState(
                currentScore = 0,
                gameRunning = false,
                circles = emptyList()
            )
        }
    }

    fun onEvent(event: AppEvent) {
        when (event) {
            is AppEvent.ClickCircle -> {
                val newCircleList = state.value.circles.filter { circle ->
                    val vecX = event.clickPosition.x - circle.position.x
                    val vecY = event.clickPosition.y - circle.position.y
                    val vecLen = sqrt(vecX.pow(2) + vecY.pow(2))

                    if (vecX.absoluteValue <= (vecX / vecLen * circleRadius).absoluteValue) {
                        if (vecY.absoluteValue <= (vecY / vecLen * circleRadius).absoluteValue) {
                            state.update {
                                AppState(
                                    currentScore = it.currentScore + 1,
                                    gameRunning = it.gameRunning,
                                    circles = it.circles
                                )
                            }
                            return@filter false
                        } else {
                            return@filter true
                        }
                    } else {
                        return@filter true
                    }
                }
                state.update {
                    AppState(
                        currentScore = it.currentScore,
                        gameRunning = it.gameRunning,
                        circles = newCircleList
                    )
                }
            }
            is AppEvent.ClickStart -> {
                onGame()
            }
            else -> Unit
        }
    }

    fun updateCircleColor(timeLeft: Int): Color {
        //TODO()
        val coef: Float = timeLeft/circleDuration.toFloat()

        //RED
        var red: Float = -228.39f * coef.pow(2) +
                76.54f * coef +
                234.78f
        if (red > 255) {red = 255f}
        if (red < 0) {red = 0f}

        //GREEN
        var green: Float = -64.92f * coef.pow(2) +
                271.97f * coef +
                46.16f
        if (green > 255) {green = 255f}
        if (green < 0) {green = 0f}

        //BLUE
        var blue: Float = 301.73f * coef.pow(3) -
                37.43f * coef.pow(2) -
                323.44f * coef +
                32.57f
        if (blue > 255) {blue = 255f}
        if (blue < 0) {blue = 0f}

        Log.d("COLOR", red.toString())
        return Color(red/255, green/255, blue/255)
    }

    fun createCircle() {
        state.update {
            AppState(
                currentScore = it.currentScore,
                gameRunning = it.gameRunning,
                circles = it.circles.plusElement(
                    Circle(
                        timeLeft = circleDuration,
                        position = randomOffset(
                            radius = circleRadius,
                            width = canvasWidth,
                            height = canvasHeight,
                        ),
                        color = baseColor
                    )
                )
            )
        }
    }

    fun onGame() {
        //TODO("Change the duration from seconds to ticks")
        state.update {
            AppState(
                currentScore = 0,
                gameRunning = true,
                circles = emptyList()
            )
        }
        createCircle()
//        createDummyCircle()
        var tickCount: Int = 0

        viewModelScope.launch {
            while (state.value.gameRunning) {
                tickCount += 1
                // create circle
                if ((tickCount >= circleApparition) or (state.value.circles.isEmpty())) {
                    createCircle()
//                    createDummyCircle()
                    tickCount = 0
                }

                val temp_circleList: MutableList<Circle> = mutableListOf()

                for (circle in state.value.circles) {
                    if (circle.timeLeft - tickSpeed <= 0) {
                        // TODO("save the current score to high score if needed")
                        state.update {
                            AppState(
                                currentScore = it.currentScore,
                                gameRunning = false,
                                circles = emptyList()
                            )
                        }
                    } else {
                        temp_circleList.add(
                            circle.copy(
                                timeLeft = circle.timeLeft - tickSpeed,
                                color = updateCircleColor(circle.timeLeft)
                            )
                        )
                    }
                }
                state.update {
                    AppState(
                        currentScore = it.currentScore,
                        gameRunning = it.gameRunning,
                        circles = temp_circleList.toList()
                    )
                }
                delay(tickSpeed.toLong())
            }
            if(preference.getHighScore() < state.value.currentScore){
                preference.setHighScore(state.value.currentScore)
            }
        }
    }

    fun randomOffset(radius: Float, width: Int, height: Int): Offset {
        return Offset(
            x = Random.nextInt(radius.roundToInt(), width - radius.roundToInt()).toFloat(),
            y = Random.nextInt(radius.roundToInt(), height - radius.roundToInt()).toFloat()
        )
    }

    fun createDummyCircle() {
        state.update {
            AppState(
                currentScore = it.currentScore,
                gameRunning = it.gameRunning,
                circles = it.circles.plusElement(
                    Circle(
                        timeLeft = circleDuration,
                        position = Offset(canvasWidth.toFloat(), canvasHeight.toFloat()),
                        color = baseColor
                    )
                )
            )
        }
    }
}
