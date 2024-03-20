package com.fekent.dndlockpick.data

data class Mode(
    val difficulty: String,
    val modeTitle: String,
    val modeTumblers: Int,
    val modeTimeLimit: Int,
)

val modes = listOf(
    Mode("Easy","DC: 0-5", 3, 10), //easy
    Mode("Medium","DC: 6-10", 6, 30), //medium
    Mode("Hard","DC: 11-15", 9, 40), //hard
    Mode("Brutal","DC: 16-20", 12, 50), //brutal
    Mode("Impossible","DC: 20+", 15, 50) //impossible
)