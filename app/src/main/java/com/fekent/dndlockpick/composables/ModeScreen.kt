@file:OptIn(ExperimentalMaterial3Api::class)

package com.fekent.dndlockpick.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fekent.dndlockpick.ui.theme.DNDLockpickTheme

@Composable
fun ModeScreen(back: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ModeBar(back = back)

    }
}

@Composable
fun ModeBar(back: () -> Unit) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = "Mode Selection", fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }, actions = {
        IconButton(onClick = { back() }) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Back")
        }
    },
        modifier = Modifier
            .padding(4.dp)
            .shadow(
                elevation = 5.dp,
                spotColor = Color.DarkGray,
                shape = RoundedCornerShape(10.dp)
            )
    )
}


@Preview(showSystemUi = true)
@Composable
private fun ModePreview() {
    DNDLockpickTheme {
        ModeScreen({})
    }
}