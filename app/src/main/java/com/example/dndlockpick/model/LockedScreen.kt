package com.example.dndlockpick.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dndlockpick.R

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
        Image(
            painter = painterResource(id = R.drawable.lockpick_icon_grey),
            contentDescription = "Locked Icon",
            modifier = Modifier.size(450.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun LockedScreenPreview() {
    LockedScreen {

    }
}