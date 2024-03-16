package com.fekent.dndlockpick.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fekent.dndlockpick.ui.theme.DNDLockpickTheme

@Composable
fun ModeScreen() {
    Column(modifier = Modifier.fillMaxWidth()) {

    }
}

@Preview
@Composable
private fun ModePreview() {
    DNDLockpickTheme {
        ModeScreen()
    }
}