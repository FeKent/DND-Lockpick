@file:Suppress("DEPRECATION")

package com.fekent.dndlockpick.composables

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fekent.dndlockpick.R
import com.fekent.dndlockpick.viewmodel.LockpickViewModel
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


@Composable
fun TumblerScreen(
    navController: NavHostController,
    backHome: () -> Unit,
    lockpickViewModel: LockpickViewModel = viewModel()
) {
    val state = lockpickViewModel.tumblerCount
    val timer = lockpickViewModel.timeLeft.intValue
    val timerRunning = lockpickViewModel.countDownRunning.value
    val initialTime = lockpickViewModel.initialTime.absoluteValue
    val order = lockpickViewModel.orderShuffled
    val selectedTumblers = lockpickViewModel.selectedTumblers
    Log.i("Correct Order", order.toString())

    val colorTransition = remember { Animatable(Color.White) }
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?

    val shake = remember { Animatable(0f) }
    var trigger by remember { mutableLongStateOf(0L) }
    lockpickViewModel.timerExpires(navController = navController)

    LaunchedEffect(trigger) {
        if (trigger != 0L) {
            for (i in 0..10) {
                when (i % 2) {
                    0 -> shake.animateTo(5f, spring(stiffness = 100_000f))
                    else -> shake.animateTo(-5f, spring(stiffness = 100_000f))
                }
            }
            shake.animateTo(0f)
        }
    }

    BackHandler {
        lockpickViewModel.showExitAlertDialog.value = true
        lockpickViewModel.countDownRunning.value = false
    }

    if (lockpickViewModel.showExitAlertDialog.value) {
        lockpickViewModel.countDownRunning.value = false
        ExitAlertDialog(
            onDismiss = { lockpickViewModel.showExitAlertDialog.value = false },
            onConfirm = { backHome(); lockpickViewModel.showExitAlertDialog.value = false })
    }

    Column {
        TumblerScreenBar { lockpickViewModel.showExitAlertDialog.value = true }
        Column {
            Box(Modifier.fillMaxWidth(), Alignment.Center) {
                LaunchedEffect(key1 = timer, timerRunning) {
                    while (timer > 0 && timerRunning) {
                        delay(1000L)
                        lockpickViewModel.timeLeft.intValue--
                    }
                }
                Row {
                    Text(text = "Time Left:", modifier = Modifier.align(Alignment.CenterVertically))
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "${lockpickViewModel.timeLeft.intValue}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.size(32.dp))
                    Row {
                        IconButton(onClick = {
                            // Toggle the countdown state when the pause button is clicked
                            lockpickViewModel.countDownRunning.value = !lockpickViewModel.countDownRunning.value
                        }) {
                            val icon = if (timerRunning) {
                                painterResource(id = R.drawable.pause)
                            } else {
                                painterResource(id = R.drawable.play)
                            }
                            Icon(painter = icon, contentDescription = "Pause/Play Timer")
                        }
                        IconButton(onClick = {
                            lockpickViewModel.timeLeft.intValue = initialTime
                            lockpickViewModel.countDownRunning.value = true
                            lockpickViewModel.selectedTumblers.clear()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.restart),
                                contentDescription = "Restart Timer"
                            )
                        }

                    }

                }

            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(100.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(count = state) { i ->
                    val isSelected = selectedTumblers.contains(i)
                    val backgroundColor = if (isSelected) Color(65, 178, 139, 255) else if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray

                    if (lockpickViewModel.countDownRunning.value) {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(4.dp))
                                .border(width = 2.dp, color = Color.Black)
                                .background(backgroundColor)
                                .offset { IntOffset(shake.value.roundToInt(), y = 0) }
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .semantics { contentDescription = "Tumbler $i" }
                                .clickable  {
                                    if (!isSelected) {
                                        // Check if the selected tumbler is the correct one
                                        val isCorrectTumbler = i == order[selectedTumblers.size]
                                        if (!isCorrectTumbler) {
                                            // Vibrate the phone for wrong tumbler press
                                            if (vibrator != null) {
                                                val vibrationEffect = VibrationEffect.createOneShot(
                                                    200,
                                                    VibrationEffect.DEFAULT_AMPLITUDE
                                                )
                                                vibrator.vibrate(vibrationEffect)
                                                trigger = System.currentTimeMillis()
                                            }
                                        }
                                        lockpickViewModel.toggleTumblerSelection(
                                            i,
                                            navController = navController
                                        )
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(7.dp)
                                    .aspectRatio(1f)
                                    .border(1.dp, color = Color(65, 178, 139, 255))
                                    .semantics { contentDescription = "Tumbler $i" }

                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(4.dp))
                                .border(width = 2.dp, color = Color.Black)
                                .background(backgroundColor)
                                .offset { IntOffset(shake.value.roundToInt(), y = 0) }
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .semantics { contentDescription = "Tumbler $i" },
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(7.dp)
                                    .aspectRatio(1f)
                                    .border(1.dp, color = Color(65, 178, 139, 255))
                                    .semantics { contentDescription = "Tumbler $i" }
                            )
                        }
                    }


                    LaunchedEffect(backgroundColor) {
                        colorTransition.animateTo(backgroundColor)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TumblerScreenBar(backHome: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Tumblers",
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { backHome() }) {
                Icon(Icons.Filled.KeyboardArrowLeft, "Back")
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

@Preview(showSystemUi = true, fontScale = 2f)
@Composable
fun TumblerUnpopulatedPreview() {
    val navController = rememberNavController()

    val viewModel = remember {
        LockpickViewModel(
            savedStateHandle = SavedStateHandle(mapOf("tumblerCount" to "0", "timeLimit" to "60"))
        )
    }

    TumblerScreen(
        navController = navController,
        backHome = { /*TODO*/ },
        lockpickViewModel = viewModel
    )
}

@Preview(showSystemUi = true, widthDp = 600)
@Composable
fun TumblerPopulatedPreview() {
    val navController = rememberNavController()

    val viewModel = remember {
        LockpickViewModel(
            savedStateHandle = SavedStateHandle(mapOf("tumblerCount" to "5", "timeLimit" to "60"))
        )
    }

    TumblerScreen(
        navController = navController,
        backHome = { /*TODO*/ },
        lockpickViewModel = viewModel
    )
}
