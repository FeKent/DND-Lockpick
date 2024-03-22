package com.fekent.dndlockpick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fekent.dndlockpick.composables.LandingScreen
import com.fekent.dndlockpick.composables.ModeScreen
import com.fekent.dndlockpick.composables.ResultsScreen
import com.fekent.dndlockpick.composables.TumblerScreen
import com.fekent.dndlockpick.data.modes
import com.fekent.dndlockpick.ui.theme.DNDLockpickTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DNDLockpickTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DNDLockpick()
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Landing : Screen("Landing")
    object Tumbler : Screen("Tumbler/{tumblerCount}/{timeLimit}")
    object Results : Screen("Results/{results}")
    object Mode : Screen("Mode")
}

@Composable
fun DNDLockpick() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Mode.route) {
        composable(Screen.Landing.route) {
            LandingScreen(modeSelection = { navController.navigate(Screen.Mode.route) }) { tumblerCount, timeLimit ->
                navController.navigate(
                    "Tumbler/$tumblerCount/$timeLimit"
                )
            }
        }
        composable(Screen.Tumbler.route) {
            TumblerScreen(
                navController = navController,
                backHome = { navController.navigate(Screen.Landing.route) }
            )
        }

        composable(Screen.Results.route) {
            ResultsScreen(
                navController = navController,
            )
        }
        composable(Screen.Mode.route) {
            ModeScreen(
                back = { navController.navigate(Screen.Landing.route) },
                modes,
                modeChoice = { mode -> navController.navigate("Tumbler/${mode.modeTumblers}/${mode.modeTimeLimit}") },
                customScreen = {navController.navigate(Screen.Landing.route)})
        }
    }
}