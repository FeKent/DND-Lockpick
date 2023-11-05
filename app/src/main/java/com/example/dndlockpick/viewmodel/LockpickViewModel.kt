package com.example.dndlockpick.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class LockpickViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val tumblerCount: Int = savedStateHandle.get<String>("tumblerCount")!!.toInt()

    val orderShuffled by derivedStateOf {
        val tumblerValue = tumblerCount
        (0 until tumblerValue).toList().shuffled()
    }


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
        } else {
            // Reset the selection if the user presses the wrong tumblerIndex
            selectedTumblers.clear()
        }
    }
}