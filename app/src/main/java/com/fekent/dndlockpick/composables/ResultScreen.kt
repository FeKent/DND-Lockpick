@file:Suppress("DEPRECATION")

package com.fekent.dndlockpick.composables

import android.content.Context.VIBRATOR_SERVICE
import android.media.MediaPlayer
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fekent.dndlockpick.R
import com.fekent.dndlockpick.viewmodel.ResultsViewModel
import kotlinx.coroutines.delay

@Composable
fun ResultsScreen(resultsViewModel: ResultsViewModel = viewModel(), navController: NavController) {
    val context = LocalContext.current
    val results = resultsViewModel.results
    val vibrator: Vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator

    BackHandler {
        returnToLanding(navController)
    }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(64.dp))
        Text(
            text = resultsViewModel.message, fontSize = 32.sp, textAlign = TextAlign.Center,
            lineHeight = 40.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Spacer(modifier = Modifier.size(32.dp))
        Button(onClick = { returnToLanding(navController) }) {
            Text(text = "Return to Menu")
        }
        Spacer(modifier = Modifier.size(32.dp))

        val imageResource1 = painterResource(R.drawable.lockpick_icon_grey)

        val resultPainter = run {
            if (results) {
                painterResource(R.drawable.lockpick_unlocked)
            } else {
                painterResource(R.drawable.lockpick_locked)
            }
        }

        // Create a Crossfade composable to transition between the images
        var unlocked by remember { mutableStateOf(false) }

        Crossfade(targetState = unlocked, label = "Unlocking Animation") { isUnlocked ->
            val alpha by animateFloatAsState(if (isUnlocked) 1f else 0f, label = "Unlock Animation")

            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = imageResource1,
                    contentDescription = "Locked Lock",
                    modifier = Modifier
                        .alpha(1 - alpha)
                        .size(450.dp) // Fade out
                )
                Image(
                    painter = resultPainter,
                    contentDescription = "Results Lock",
                    modifier = Modifier
                        .alpha(alpha)
                        .size(450.dp) // Fade in
                )
            }
        }

        LaunchedEffect(Unit) {
            delay(1500) // 2 seconds delay
            val mp = MediaPlayer.create(context, R.raw.lock_sound)
            mp.start()
            unlocked = true


            if (!results) {
                val vibrationEffect = VibrationEffect.createOneShot(
                    500,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
                vibrator.vibrate(vibrationEffect)
            }
        }
    }
}

private fun returnToLanding(navController: NavController) {
    navController.popBackStack("Landing", inclusive = false)
}
