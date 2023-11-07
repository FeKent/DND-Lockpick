package com.example.dndlockpick

import com.example.dndlockpick.model.LandingScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dndlockpick.model.TumblerScreen
import com.example.dndlockpick.model.UnlockedScreen
import com.example.dndlockpick.ui.theme.DNDLockpickTheme
import com.example.dndlockpick.viewmodel.LandingViewModel
import com.example.dndlockpick.viewmodel.LockpickViewModel

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
    object Tumbler : Screen("Tumbler/{tumblerCount}")
    object Unlocked : Screen("Unlocked")
}

@androidx.compose.runtime.Composable
fun DNDLockpick() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Landing.route) {
        composable(Screen.Landing.route) { LandingScreen(start = { navController.navigate("Tumbler/$it") }) }
        composable(Screen.Tumbler.route) {
            TumblerScreen(
                backHome = { navController.navigate(Screen.Landing.route) },
                navController = navController
            )
        }
        composable(Screen.Unlocked.route) { UnlockedScreen() }
    }

}