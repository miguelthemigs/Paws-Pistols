package com.example.reactiongame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reactiongame.ui.theme.ReactionGameTheme
import com.example.reactiongame.view.GameScreen
import com.example.reactiongame.view.StartScreen
import com.example.reactiongame.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReactionGameTheme {
            val navController = rememberNavController()
                val gameViewModel: GameViewModel = viewModel()

                NavHost(navController = navController, startDestination = "startScreen") {
                    composable("startScreen") {
                        StartScreen(onStartClick = { navController.navigate("gameScreen") })
                    }

                    composable("gameScreen") {
                        GameScreen()
                    }
                }



            }
        }
    }
}

