package com.example.dndlockpick.viewmodel

import androidx.annotation.DrawableRes
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.dndlockpick.R

class ResultsViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val results: Int = savedStateHandle.get<String>("results")!!.toInt()

    var message: String = ""

    fun lockpickOutcome(results: Int){
        if (results == 1){
            message = "YOU DID IT!"
        } else {
            message = "You failed to pick the lock"
        }
    }
}