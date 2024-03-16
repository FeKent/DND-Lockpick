@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.fekent.dndlockpick.composables

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
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fekent.dndlockpick.R
import com.fekent.dndlockpick.viewmodel.LandingViewModel
import kotlin.math.absoluteValue


@Composable
fun LandingScreen(
    modifier: Modifier = Modifier,
    landingViewModel: LandingViewModel = viewModel(),
    modeSelection: () -> Unit,
    start: (Int, Any?) -> Unit
) {
    val viewState by landingViewModel.viewState.collectAsState()

    val showBothDialog = remember { mutableStateOf(false) }
    if (showBothDialog.value) {
        AlertDialogs(
            onDismiss = { showBothDialog.value = false },
            onConfirm = { showBothDialog.value = false },
            message = "You still need to choose the amount of tumblers and set a time limit"
        )
    }

    val showTumblerDialog = remember { mutableStateOf(false) }
    if (showTumblerDialog.value) {
        AlertDialogs(
            onDismiss = { showTumblerDialog.value = false },
            onConfirm = { showTumblerDialog.value = false },
            message = "You still need to choose the amount of tumblers"
        )
    }

    val showTimerDialog = remember { mutableStateOf(false) }
    if (showTimerDialog.value) {
        AlertDialogs(
            onDismiss = { showTimerDialog.value = false },
            onConfirm = { showTimerDialog.value = false },
            message = "You still need to set a time limit"
        )
    }

    val showTumblerLimitDialog = remember { mutableStateOf(false) }
    if (showTumblerLimitDialog.value) {
        AlertDialogs(
            onDismiss = { showTumblerLimitDialog.value = false },
            onConfirm = { showTumblerLimitDialog.value = false },
            message = "Lock exceeds 15 tumbler limit"
        )
    }

    val showTimerLimitDialog = remember { mutableStateOf(false) }
    if (showTimerLimitDialog.value) {
        AlertDialogs(
            onDismiss = { showTimerLimitDialog.value = false },
            onConfirm = { showTimerLimitDialog.value = false },
            message = "Timer exceeds 120 second limit"
        )
    }

    Column(Modifier.fillMaxWidth()) {
        LandingScreenBar(modeSelection = modeSelection)
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
            Text(text = "Number of Tumblers:")
            TextField(
                value = if (viewState.tumblerCount.absoluteValue == 0) {
                    ""
                } else {
                    viewState.tumblerCount.toString()
                },
                onValueChange = {
                    landingViewModel.tumblerCount.value = it.toIntOrNull() ?: 0
                    Log.i("state", viewState.tumblerCount.toString())
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                placeholder = {
                    Row(
                        modifier = Modifier.width(248.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "0")
                    }
                },
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )
            Spacer(modifier = Modifier.size(24.dp))
            Text(text = "Time Limit:")

            TextField(
                value = if (viewState.timeLimit.absoluteValue == 0) {
                    ""
                } else {
                    viewState.timeLimit.toString()
                },
                onValueChange = {
                    landingViewModel.timeLimit.value = it.toIntOrNull() ?: 0
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                placeholder = {
                    Row(
                        modifier = Modifier.width(248.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "0")
                    }
                },
                label = { Text(text = "Seconds") },
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )
            Spacer(modifier = Modifier.size(40.dp))
            TextButton(onClick = {
                /* TODO: Want to add an upper limit on tumblers/time limit - 15 tumblers, 2mins*/
                if (viewState.tumblerCount == 0 && viewState.timeLimit == 0) {
                    showBothDialog.value = true
                } else if (viewState.tumblerCount == 0) {
                    showTumblerDialog.value = true
                } else if (viewState.timeLimit == 0) {
                    showTimerDialog.value = true
                } else if (viewState.tumblerCount > 15){
                    showTumblerLimitDialog.value = true
                } else if (viewState.timeLimit > 120){
                    showTimerLimitDialog.value = true
                } else {
                    start(viewState.tumblerCount, viewState.timeLimit)
                }
            })
            {
                Text(text = "GO!", fontSize = 40.sp)
            }
            Spacer(modifier = Modifier.size(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreenBar(modeSelection: () -> Unit) {
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
            IconButton(onClick = { modeSelection() }) {
                Icon(Icons.Filled.Home, "Mode Selection Menu")
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
fun LandingPreview() {
    LandingScreen(modeSelection = {}, start = { _, _ -> })
}

@Preview(fontScale = 2f, widthDp = 400, heightDp = 600)
@Composable
fun LandingPreview2() {
    LandingScreen(modeSelection = {}, start = { _, _ -> })
}