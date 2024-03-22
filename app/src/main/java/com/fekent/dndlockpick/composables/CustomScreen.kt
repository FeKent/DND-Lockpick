@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.fekent.dndlockpick.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fekent.dndlockpick.viewmodel.CustomViewModel


@Composable
fun CustomScreen(
    modifier: Modifier = Modifier,
    customViewModel: CustomViewModel = viewModel(),
    modeSelection: () -> Unit,
    start: (Int, Any?) -> Unit
) {
    val viewState by customViewModel.viewState.collectAsState()

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

    var sliderPosition by remember { mutableIntStateOf(0) }

    Column(Modifier.fillMaxWidth()) {
        CustomScreenBar(modeSelection = modeSelection)

        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Number of Tumblers:")
            Slider(
                value = sliderPosition.toFloat(),
                onValueChange = {
                    sliderPosition = it.toInt(); customViewModel.tumblerCount.value = it.toInt()
                },
                track = {
                    SliderDefaults.Track(
                        sliderState = SliderState(),
                        modifier = Modifier.scale(1f, 3f),
                        colors = SliderDefaults.colors(
                            activeTrackColor = Color(65, 178, 139, 255),
                            inactiveTrackColor = Color(65, 178, 139, 255),
                        )
                    )
                },
                colors = SliderDefaults.colors(thumbColor = Color(105, 20, 15)),
                steps = 15,
                valueRange = 0f..15f,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Text(
                text = sliderPosition.toString(),
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.size(24.dp))
            Text(text = "Time Limit:")
            CircularSlider(
                modifier = Modifier
                    .size(250.dp),
                initialValue = 0,
                primaryColor = if (isSystemInDarkTheme()) Color(65, 178, 139, 255) else Color(
                    105,
                    20,
                    15
                ),
                secondaryColor = Color.DarkGray,
                circleRadius = 230f,
                onPositionChange = {
                    customViewModel.timeLimit.value = it
                }
            )
            Spacer(modifier = Modifier.size(40.dp))
            TextButton(onClick = {
                if (viewState.tumblerCount == 0 && viewState.timeLimit == 0) {
                    showBothDialog.value = true
                } else if (viewState.tumblerCount == 0) {
                    showTumblerDialog.value = true
                } else if (viewState.timeLimit == 0) {
                    showTimerDialog.value = true
                } else if (viewState.tumblerCount > 15) {
                    showTumblerLimitDialog.value = true
                } else if (viewState.timeLimit > 120) {
                    showTimerLimitDialog.value = true
                } else {
                    start(viewState.tumblerCount, viewState.timeLimit)
                }
            })
            {
                Text(text = "GO!", fontSize = 40.sp, color = Color(65, 178, 139, 255))
            }
            Spacer(modifier = Modifier.size(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomScreenBar(modeSelection: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Custom Mode",
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
fun CustomPreview() {
    CustomScreen(modeSelection = {}, start = { _, _ -> })
}

@Preview(fontScale = 2f, widthDp = 400, heightDp = 600)
@Composable
fun CustomPreview2() {
    CustomScreen(modeSelection = {}, start = { _, _ -> })
}