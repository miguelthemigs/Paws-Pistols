package com.example.reactiongame.view

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reactiongame.R
import com.example.reactiongame.model.ScoreManager
import com.example.reactiongame.viewmodel.GameViewModel

/*
 Will have:
 - start button and shake to start
 - show highest score
*/

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StartScreen(
    onStartClick: () -> Unit,
    gameViewModel: GameViewModel = viewModel()
) {
    val highestScore = if (ScoreManager.getBestTime() != Long.MAX_VALUE) {
        ScoreManager.getBestTime().toString()
    } else {
        "N/A"
    }

    // Register sensor listener when StartScreen is composed.
    LaunchedEffect(Unit) {
        gameViewModel.registerAccelerometerListener()
    }

    if (gameViewModel.isGameInProgress) { // if it was shaked, it is the same as the button
        onStartClick()
        gameViewModel.unregisterAccelerometerListener()
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Paws & Pistols") },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Background image
            Image(
                painter = painterResource(id = R.drawable.bg),
                contentDescription = "Background Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Dark overlay to improve text contrast
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // SPINNING CAT ???
                val infiniteTransition = rememberInfiniteTransition()
                val rotation by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 4000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )

                Image(
                    painter = painterResource(id = R.drawable.cat),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .graphicsLayer {
                            rotationZ = rotation
                        }
                )

                Spacer(modifier = Modifier.height(16.dp))


                // Title Text
                Box(
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.8f))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "\uD83D\uDC3E Welcome to Paws & Pistols! \uD83D\uDC3E",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = Color.Black,
                            shadow = Shadow(
                                color = Color.Gray,
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Instructions Text
                Box(
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.8f))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Press the Button or Shake the Device to start!",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { onStartClick() }) {
                    Text(text = "Start", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Highest Score Text
                Box(
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.8f))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Highest score: $highestScore",
                        style = TextStyle(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            shadow = Shadow(
                                color = Color.Gray,
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        )
                    )
                }
            }
        }
    }
}
