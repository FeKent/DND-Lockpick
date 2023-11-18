@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.dndlockpick.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dndlockpick.R
import com.example.dndlockpick.viewmodel.LandingViewModel


@Composable
fun LandingScreen(
    modifier: Modifier = Modifier,
    landingViewModel: LandingViewModel = viewModel(),
    start: (Int, Any?) -> Unit,
) {
    val tumblerState = landingViewModel.tumbler.collectAsState()
    val timeState = landingViewModel.timeLimit.collectAsState()

    Column(Modifier.fillMaxWidth()) {
        LandingScreenBar()
        Image(
            painter = painterResource(id = R.drawable.js_lockpick_shortened),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 20.dp)
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Choose amount of tumblers:")
            TextField(
                value = tumblerState.value?.toString() ?: "",
                onValueChange = {
                    landingViewModel.tumbler.value = it.toIntOrNull()
                    Log.i("state", tumblerState.value?.toString() ?: "")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                placeholder = {
                    Row(
                        modifier = Modifier.width(248.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "0")
                    }
                },
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )
            Spacer(modifier = Modifier.size(24.dp))
            Text(text = "Time Limit")

            TextField(
                value = timeState.value?.toString() ?: "",
                onValueChange = {
                    landingViewModel.timeLimit.value = it.toIntOrNull()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                placeholder = {
                    Row(
                        modifier = Modifier.width(248.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "0")
                    }
                },
                label = { Text(text = "Seconds") },
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )
            Spacer(modifier = Modifier.size(40.dp))
            TextButton(onClick = { start(tumblerState.value ?: 0, timeState.value ?: 0) }) {
                Text(text = "GO!", fontSize = 40.sp)
            }
            Spacer(modifier = Modifier.size(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreenBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Lockpick Mini-Game",
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Menu, "Menu")
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