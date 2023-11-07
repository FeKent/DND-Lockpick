package com.example.dndlockpick

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dndlockpick.model.LandingScreen
import com.example.dndlockpick.model.TumblerScreen
import com.example.dndlockpick.model.UnlockedScreen
import com.example.dndlockpick.ui.theme.DNDLockpickTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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
    object Tumbler : Screen("Tumbler/{tumblerCount}")
    object Unlocked : Screen("Unlocked")
}

@RequiresApi(Build.VERSION_CODES.O)
@androidx.compose.runtime.Composable
fun DNDLockpick() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Landing.route) {
        composable(Screen.Landing.route) { LandingScreen(start = { navController.navigate("Tumbler/$it") }) }
        composable(Screen.Tumbler.route) {
            TumblerScreen(
                navController = navController,
                backHome = { navController.navigate(Screen.Landing.route) }
            )
        }
        composable(Screen.Unlocked.route) {
            UnlockedScreen(backHome = { navController.navigate(Screen.Landing.route) })
        }
    }
}