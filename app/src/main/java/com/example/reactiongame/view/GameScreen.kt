package com.example.reactiongame.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reactiongame.R
import com.example.reactiongame.viewmodel.GameViewModel

@Composable
fun GameScreen(
        gameViewModel: GameViewModel = viewModel()
) {
        // start the game automatically when the screen is composed.
        LaunchedEffect(Unit) {
                if (!gameViewModel.isGameInProgress) {
                        gameViewModel.startGame()
                }
        }

        Column(modifier = Modifier.fillMaxSize()) {
                // Top half for Player 2 (rotated 180° so it appears right-side up for them)
                Box(
                        modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .graphicsLayer { rotationZ = 180f }
                                .background(Color.LightGray)
                                .clickable {
                                        if (gameViewModel.roundActive) {
                                                gameViewModel.onPlayer2Tap()
                                        } else {
                                                gameViewModel.onWrongTapPlayer2()
                                        }
                                },
                        contentAlignment = Alignment.Center
                ) {
                        Column(
                                modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                        ) {
                                Text(
                                        text = "Player 2 Score: ${gameViewModel.player2Score} | ${gameViewModel.player2ReactionTime}",
                                        style = MaterialTheme.typography.headlineMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Image(
                                        painter = painterResource(id = R.drawable.shooter),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                                .size(130.dp)
                                                .graphicsLayer { rotationZ = 280f }

                                )
                        }
                }
                // Divider line between halves
                Box(
                        modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color.Black)
                )
                // Middle section to display the round message
                Box(
                        modifier = Modifier
                                .fillMaxWidth()
                                .graphicsLayer { rotationZ = 180f }
                                .height(75.dp)
                                .background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                ) {
                        Text(
                                text = gameViewModel.roundMessage,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White
                        )
                }
                Box(
                        modifier = Modifier
                                .fillMaxWidth()
                                .height(75.dp)
                                .background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                ) {
                        Text(
                                text = gameViewModel.roundMessage,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White
                        )
                }

                // Divider line between middle and bottom
                Box(
                        modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color.Black)
                )
                // Bottom half for Player 1
                Box(
                        modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .background(Color.LightGray)
                                .clickable {
                                        if (gameViewModel.roundActive) {
                                                gameViewModel.onPlayer1Tap()
                                        } else {
                                                gameViewModel.onWrongTapPlayer1()
                                        }
                                },
                        contentAlignment = Alignment.Center
                ) {
                        Column(
                                modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                        ) {
                                Text(
                                        text = "Player 1 Score: ${gameViewModel.player1Score} | ${gameViewModel.player1ReactionTime}",
                                        style = MaterialTheme.typography.headlineMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Image(
                                        painter = painterResource(id = R.drawable.shooter),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                                .size(130.dp)
                                                .graphicsLayer { rotationZ = 280f }

                                )
                        }
                }

        }
}
