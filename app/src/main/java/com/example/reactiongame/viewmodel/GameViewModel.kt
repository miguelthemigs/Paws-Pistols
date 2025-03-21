package com.example.reactiongame.viewmodel

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.reactiongame.model.Match

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private var currentMatch by mutableStateOf(Match())
    var isGameInProgress by mutableStateOf(false)

    private val sensorManager =
        getApplication<Application>().getSystemService(SensorManager::class.java)
    // Use the accelerometer for detecting shakes
    val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // Threshold for detecting a shake.
    private val shakeThreshold = 30.5f

    // Cooldown time in milliseconds between shake detections
    private val shakeWaitTime = 1000L // 1 second
    private var lastShakeTime = 0L

    // SensorEventListener to detect shakes
    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                Log.d("GameViewModel", "Accelerometer event detected")
                // Get acceleration values
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                // Compute the acceleration magnitude
                val accelerationMagnitude = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                // Subtract Earth's gravity to get the net acceleration change
                val netAcceleration = accelerationMagnitude - SensorManager.GRAVITY_EARTH

                val currentTime = System.currentTimeMillis()
                // Check if the net acceleration exceeds the threshold and if enough time has passed
                if (netAcceleration > shakeThreshold && currentTime - lastShakeTime > shakeWaitTime) {
                    lastShakeTime = currentTime
                    if (!isGameInProgress) {
                        startGame()
                        isGameInProgress = true
                    }
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // Not used
        }
    }

    // Call this function to start listening for shakes using the accelerometer
    fun registerAccelerometerListener() {
        sensor?.also { sensor ->
            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    // Call this function when you no longer need to listen for shakes
    fun unregisterAccelerometerListener() {
        sensorManager.unregisterListener(sensorEventListener)
    }

    // Initialize the game state, reset scores, and prepare for a new match.
    fun startGame() {
        currentMatch = Match() // new Match() resets the scores and rounds played
    }

    // Call this when the game ends.
    fun endGame() {
        isGameInProgress = false
        // Re-register the sensor listener so the next shake can start a new game.
        registerAccelerometerListener()
    }

    // Other functions (playRound, checkGameStatus, getWinner, resetGame, updateBestTime) remain unchanged.
}
