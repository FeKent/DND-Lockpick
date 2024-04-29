package com.fekent.dndlockpick.test

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.fekent.dndlockpick.composables.CustomScreen
import org.junit.Rule
import org.junit.Test

class DndLockpickUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun customScreenUnitTest(){
        composeTestRule.setContent {
            CustomScreen(modeSelection = {}, start = { _, _ ->
            })
        }

        composeTestRule.onNodeWithText("Number of Tumblers:").assertExists()

    }

}