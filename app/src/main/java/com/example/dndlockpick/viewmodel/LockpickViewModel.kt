package com.example.dndlockpick.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LockpickViewModel : ViewModel(){
    val tumbler = MutableStateFlow("")
    val timeLimit = MutableStateFlow("")
}