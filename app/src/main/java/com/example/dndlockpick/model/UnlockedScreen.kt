package com.example.dndlockpick.model

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UnlockedScreen(backHome: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(64.dp))
        Text(text = "YOU DID IT")
        Spacer(modifier = Modifier.size(32.dp))
        Button(onClick = { backHome() }) {
            Text(text = "Return to Menu")
        }
    }

}