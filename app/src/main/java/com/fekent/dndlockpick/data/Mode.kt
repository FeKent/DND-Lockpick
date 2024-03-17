package com.fekent.dndlockpick.data

data class Mode(
    val modeTitle: String,
    val modeTumblers: Int,
    val modeTimeLimit: Int,
)

val modes = listOf(
    Mode("Easy", 2, 10),
    Mode("Medium", 5, 30),
    Mode("Hard", 10, 40),
    Mode("Brutal", 15, 50)
)