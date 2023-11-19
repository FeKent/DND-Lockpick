package com.example.dndlockpick.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

class LockpickViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val tumblerCount: Int = savedStateHandle.get<String>("tumblerCount")!!.toInt()
    private val timeLimit: Int = savedStateHandle.get<String>("timeLimit")!!.toInt()

    var timeLeft = mutableIntStateOf(timeLimit)
    val initialTime = timeLimit

    val orderShuffled = run {
        val tumblerValue = tumblerCount
        (0 until tumblerValue).toList().shuffled()
    }
    // run = runs a bunch of lines and returns last value

    val countDownRunning = mutableStateOf(true)

    val selectedTumblers = mutableStateListOf<Int>()
    private var results: Boolean? = null

    fun toggleTumblerSelection(tumblerIndex: Int, navController: NavHostController) {
        val tumblerValue = tumblerCount
        val nextExpectedIndex = selectedTumblers.size

        if (tumblerIndex == orderShuffled[nextExpectedIndex] && nextExpectedIndex < tumblerValue) {
            if (selectedTumblers.contains(tumblerIndex)) {
                selectedTumblers.remove(tumblerIndex)
            } else {
                selectedTumblers.add(tumblerIndex)
            }

            if (nextExpectedIndex == tumblerValue - 1) {
                results = true
                unlocked(navController = navController)
                Log.i("LastTumbler", "Last tumbler in the sequence clicked")
            }
        } else {
            // Reset the selection if the user presses the wrong tumblerIndex
            selectedTumblers.clear()
        }
    }

    fun timerExpires(navController: NavHostController){
        val timer = timeLeft.intValue

        if (timer == 0){
            results = false
            locked(navController = navController)
        }
    }


    private fun locked(navController: NavHostController){
        navController.navigate("Results/$results")
    }

    private fun unlocked(navController: NavHostController){
        navController.navigate("Results/$results")
    }

}