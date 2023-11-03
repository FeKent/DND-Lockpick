package com.example.dndlockpick.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LandingScreenViewModel : ViewModel(){
    val tumbler = MutableStateFlow("")
    val timeLimit = MutableStateFlow("")
}