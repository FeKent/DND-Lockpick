package com.example.dndlockpick.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class LockpickViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val tumblerCount: Int = savedStateHandle.get<String>("tumblerCount")!!.toInt()

    val orderShuffled = run {
        val tumblerValue = tumblerCount
        (0 until tumblerValue).toList().shuffled()
    }
    // run = runs a bunch of lines and returns last value


    val selectedTumblers = mutableStateListOf<Int>()

    fun toggleTumblerSelection(tumblerIndex: Int) {
        val tumblerValue = tumblerCount
        val nextExpectedIndex = selectedTumblers.size

        if (tumblerIndex == orderShuffled[nextExpectedIndex] && nextExpectedIndex < tumblerValue) {
            if (selectedTumblers.contains(tumblerIndex)) {
                selectedTumblers.remove(tumblerIndex)
            } else {
                selectedTumblers.add(tumblerIndex)
            }

            if (nextExpectedIndex == tumblerValue - 1) {
                // Log a message when the last tumbler is clicked
                Log.i("LastTumbler", "Last tumbler in the sequence clicked")
            }
        } else {
            // Reset the selection if the user presses the wrong tumblerIndex
            selectedTumblers.clear()
        }
    }
}