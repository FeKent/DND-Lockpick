package com.fekent.dndlockpick.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn


data class CustomViewState(
    val timeLimit: Int,
    val tumblerCount: Int
)

class CustomViewModel : ViewModel() {

    val viewState: StateFlow<CustomViewState>

    internal val timeLimit: MutableStateFlow<Int> = MutableStateFlow(0)
    internal val tumblerCount: MutableStateFlow<Int> = MutableStateFlow(0)

    init {
        viewState = combine(
            timeLimit,
            tumblerCount
        ) { timeLimit, tumblerCount ->
            CustomViewState(
                timeLimit = timeLimit,
                tumblerCount = tumblerCount
            )
        }.stateIn(viewModelScope, SharingStarted.Lazily, CustomViewState(0, 0))
    }
}