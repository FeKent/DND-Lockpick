package com.example.dndlockpick.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LandingViewModel(): ViewModel() {

    val tumbler = MutableStateFlow<Int?>(null)
    val timeLimit = MutableStateFlow<Int?>(null)

}