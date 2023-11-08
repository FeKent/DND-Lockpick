@file:Suppress("DEPRECATION")

package com.example.dndlockpick.model

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.dndlockpick.viewmodel.LockpickViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TumblerScreen(
    navController: NavHostController,
    backHome: () -> Unit,
    lockpickViewModel: LockpickViewModel = viewModel()
) {
    val state = lockpickViewModel.tumblerCount
    val order = lockpickViewModel.orderShuffled
    val selectedTumblers = lockpickViewModel.selectedTumblers
    Log.i("Correct Order", order.toString())

    val colorTransition = remember { Animatable(Color.White) }
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?

    val shake = remember { Animatable(0f) }

    Column {
        TumblerScreenBar { backHome() }
        Column {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(100.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(count = state) { i ->
                    val isSelected = selectedTumblers.contains(i)
                    val backgroundColor = if (isSelected) Color.Green else Color.White

                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(4.dp))
                            .border(width = 2.dp, color = Color.Black)
                            .background(backgroundColor)
                            .clickable {
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
                                        }
                                    }
                                    lockpickViewModel.toggleTumblerSelection(
                                        i,
                                        navController = navController
                                    )
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {}
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