package com.example.reactiongame.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.reactiongame.R
import com.example.reactiongame.model.ScoreManager

/*
 Will have:
 - start button and shake to start
 - show highest score
 */


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StartScreen() {
    val highestScore = if (ScoreManager.getBestTime() != Long.MAX_VALUE) ScoreManager.getBestTime().toString() else "N/A"
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Reaction Game", style = MaterialTheme.typography.headlineMedium) }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.cat),
                    contentDescription = "Logo: Two cowboy cats with pistols",
                    modifier = Modifier.size(250.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Welcome to the Reaction Game!", style = MaterialTheme.typography.titleLarge)
                Text(text = "Press the Button or Shake the Device to start!", style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(Alignment.CenterHorizontally))
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Start")
                }
                Text(text = "Highest score: $highestScore", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}