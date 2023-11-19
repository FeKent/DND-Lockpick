package com.example.dndlockpick.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class ResultsViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val results: Boolean = savedStateHandle.get<String>("results").toBoolean()

    var message: String = if (results) {
        "YOU DID IT!"
    } else {
        "You failed to pick the lock"
    }

}
