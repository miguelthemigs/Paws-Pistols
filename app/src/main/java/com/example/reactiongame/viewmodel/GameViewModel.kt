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
import androidx.lifecycle.viewModelScope
import com.example.reactiongame.model.Match
import com.example.reactiongame.model.ScoreManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private var currentMatch by mutableStateOf(Match())
    var isGameInProgress by mutableStateOf(false)
    // Game state variables

    var roundActive by mutableStateOf(false)
        private set
    var roundMessage by mutableStateOf("Press 'Start Round'")
        private set
    var roundNumber by mutableStateOf(0)
        private set

    // Reaction times (in ms) for each player
    var player1ReactionTime by mutableStateOf<Long?>(null)
        private set
    var player2ReactionTime by mutableStateOf<Long?>(null)
        private set

    var player1Score by mutableStateOf(0)
        private set
    var player2Score by mutableStateOf(0)
        private set

    // Time when "GO" is shown
    private var roundStartTime by mutableStateOf(0L)

    private val sensorManager =
        getApplication<Application>().getSystemService(SensorManager::class.java)
    // Use the accelerometer for detecting shakes
    val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // Threshold for detecting a shake.
    private val shakeThreshold = 28.5f

    // Cooldown time in milliseconds between shake detections
    private val shakeWaitTime = 1000L // 1 second
    private var lastShakeTime = 0L

    // SensorEventListener to detect shakes
    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                val accelerationMagnitude = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                val netAcceleration = accelerationMagnitude - SensorManager.GRAVITY_EARTH

                val currentTime = System.currentTimeMillis()
                if (netAcceleration > shakeThreshold && currentTime - lastShakeTime > shakeWaitTime) {
                    lastShakeTime = currentTime
                    if (!isGameInProgress) {
                        // Log only once when a shake is detected
                        Log.d("GameViewModel", "Shake detected: netAcceleration = $netAcceleration")
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


    // Listening for shakes using the accelerometer
    fun registerAccelerometerListener() {
        sensor?.also { sensor ->
            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun unregisterAccelerometerListener() {
        sensorManager.unregisterListener(sensorEventListener)
    }

    // Initialize the game state, reset scores, and prepare for a new match.
    fun startGame() {
        currentMatch = Match() // new Match() resets the scores and rounds played
        currentMatch.playerOneScore = 0
        currentMatch.playerTwoScore = 0
        roundNumber = 0
        roundMessage = "Press 'Start Round'"
        isGameInProgress = true
        startRound()
    }

    //  start a round – waits a random delay then shows "GO" and records start time
    fun startRound() {
        roundActive = false
        player1ReactionTime = null
        player2ReactionTime = null
        roundNumber++
        roundMessage = "Get Ready..."
        viewModelScope.launch {
            // Random delay between 1 and 5 seconds
            val delayTime = Random.nextLong(1000L, 5000L)
            delay(delayTime)
            roundMessage = "GO!"
            roundStartTime = System.currentTimeMillis()
            roundActive = true
        }
    }

    // called when Player 1 taps anywhere in his half
    fun onPlayer1Tap() {
        if (roundActive && player1ReactionTime == null) {
            player1ReactionTime = System.currentTimeMillis() - roundStartTime
            checkIfRoundComplete()
        }
    }

    // Called when Player 2 taps anywhere in his half
    fun onPlayer2Tap() {
        if (roundActive && player2ReactionTime == null) {
            player2ReactionTime = System.currentTimeMillis() - roundStartTime
            checkIfRoundComplete()
        }
    }

    // Check if both players have tapped, then evaluate the round.
    private fun checkIfRoundComplete() {
        if (player1ReactionTime != null && player2ReactionTime != null) {
            evaluateRound()
        }
    }


    // Evaluate which player reacted faster, update the score and prepare for the next round (or end the match).
    private fun evaluateRound() {
       roundActive = false
        currentMatch.playRound(player1ReactionTime!!, player2ReactionTime!!)
        player1Score = currentMatch.playerOneScore
        player2Score = currentMatch.playerTwoScore

        roundMessage = if (player1ReactionTime!! < player2ReactionTime!!){
            "Player 1 wins round: ${player1ReactionTime}ms"
        } else if (player1ReactionTime!! > player2ReactionTime!!){
            "Player 2 wins round: ${player2ReactionTime}ms"
        } else {
            "Tie: ${player1ReactionTime}ms"
        }
        // Check for a win in best of 3
        if (currentMatch.isMatchOver()) {
            roundMessage = "Match over! Winner: ${currentMatch.getWinner()}"
            endGame()

        } else {
            // Start next round after a short delay
            viewModelScope.launch {
                delay(2000L) // Wait 2 seconds before next round
                startRound()
            }
        }
    }


    // Call this when the game ends.
    private fun endGame() {
        isGameInProgress = false
        // Re-register the sensor listener so the next shake can start a new game.
        registerAccelerometerListener()
    }



}
