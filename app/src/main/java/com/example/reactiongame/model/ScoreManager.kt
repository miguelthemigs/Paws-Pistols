package com.example.reactiongame.model

// like static class in java
object ScoreManager { // object keyword creates a singleton. This means you get one single instance of that class automatically, without needing to create it explicitly.
    private var bestTime: Long = Long.MAX_VALUE

    fun updateBestTime(time: Long) {
        if (time < bestTime) {
            bestTime = time
        }
    }

    fun getBestTime(): Long {
        return bestTime
    }
}