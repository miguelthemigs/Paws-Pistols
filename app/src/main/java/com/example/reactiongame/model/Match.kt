package com.example.reactiongame.model

class Match {
    var playerOneScore: Int = 0
    var playerTwoScore: Int = 0
    var roundsPlayed: Int = 0

    fun playRound(playerOneTime: Long, playerTwoTime: Long){
        val attemptOne = ReactionAttempt(playerOneTime)
        val attemptTwo = ReactionAttempt(playerTwoTime)
        roundsPlayed++

        if (attemptOne.reactionTime < attemptTwo.reactionTime){
            playerOneScore++
        } else if (attemptOne.reactionTime > attemptTwo.reactionTime){
            playerTwoScore++
        }
        else{
            // draw
        }
        // checks if someone has the best score
        ScoreManager.updateBestTime(minOf(attemptOne.reactionTime, attemptTwo.reactionTime))
    }

    fun isMatchOver(): Boolean {
        return (playerOneScore == 2 || playerTwoScore == 2 || roundsPlayed == 3)
    }


    fun getWinner(): Int? {
        return when {
            playerOneScore == 2 -> 1
            playerTwoScore == 2 -> 2
            roundsPlayed == 3 -> 0 // zero = draw
            else -> null
        }
    }
}