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
import com.example.dndlockpick.composables.LandingScreen
import com.example.dndlockpick.composables.LockedScreen
import com.example.dndlockpick.composables.ResultsScreen
import com.example.dndlockpick.composables.TumblerScreen
import com.example.dndlockpick.composables.UnlockedScreen
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
    object Tumbler : Screen("Tumbler/{tumblerCount}/{timeLimit}")
    object Unlocked : Screen("Unlocked")
    object Locked : Screen("Locked")
    object Results : Screen("Results/{results}")
}

@RequiresApi(Build.VERSION_CODES.O)
@androidx.compose.runtime.Composable
fun DNDLockpick() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Landing.route) {
        composable(Screen.Landing.route) { LandingScreen { tumblerCount, timeLimit ->
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
//        composable(Screen.Unlocked.route) {
//            UnlockedScreen(backHome = { navController.navigate(Screen.Landing.route) })
//        }
//        composable(Screen.Locked.route) {
//            LockedScreen(backHome = { navController.navigate(Screen.Landing.route) })
//        }
        composable(Screen.Results.route){ ResultsScreen {
            navController.navigate(Screen.Landing.route)
        }}
    }
}