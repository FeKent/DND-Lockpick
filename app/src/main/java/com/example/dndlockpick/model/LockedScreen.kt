package com.example.dndlockpick.model

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dndlockpick.R
import kotlinx.coroutines.delay

@Composable
fun LockedScreen(backHome: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(64.dp))
        Text(text = "You failed to pick the lock", fontSize = 32.sp)
        Spacer(modifier = Modifier.size(32.dp))
        Button(onClick = { backHome() }) {
            Text(text = "Return to Menu")
        }
        Spacer(modifier = Modifier.size(32.dp))

        val imageResource1 = painterResource(R.drawable.lockpick_icon_grey)
        val imageResource2 = painterResource(R.drawable.lockpick_locked)

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
                    modifier = Modifier.alpha(1 - alpha).size(450.dp) // Fade out
                )
                Image(
                    painter = imageResource2,
                    contentDescription = "Unlocked Lock",
                    modifier = Modifier.alpha(alpha).size(450.dp) // Fade in
                )
            }
        }

        LaunchedEffect(Unit) {
            delay(1500) // 2 seconds delay
            unlocked = true
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LockedScreenPreview() {
    LockedScreen {}
}