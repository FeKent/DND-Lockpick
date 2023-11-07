package com.example.dndlockpick.model

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dndlockpick.R
import kotlinx.coroutines.delay

@Composable
fun UnlockedScreen(backHome: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(64.dp))
        Text(text = "YOU DID IT")
        Spacer(modifier = Modifier.size(32.dp))
        Button(onClick = { backHome() }) {
            Text(text = "Return to Menu")
        }
        Spacer(modifier = Modifier.size(32.dp))

        val imageResource1 = painterResource(R.drawable.lockpick_icon_grey)
        val imageResource2 = painterResource(R.drawable.lockpick_unlocked)

        // Create a Crossfade composable to transition between the images
        var unlocked by remember { mutableStateOf(false) }

        Crossfade(targetState = unlocked, label = "Unlocking Animation") { isUnlocked ->
            val imagePainter = if (isUnlocked) imageResource2 else imageResource1
            Image(
                painter = imagePainter,
                contentDescription = "Unlocked Lock",
                modifier = Modifier.size(450.dp)
            )
        }

        LaunchedEffect(Unit) {
            delay(2000) // 2 seconds delay
            unlocked = true
        }
    }
}