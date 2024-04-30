package com.fekent.dndlockpick.test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.fekent.dndlockpick.composables.CustomScreen
import com.fekent.dndlockpick.composables.CustomScreenBar
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

    @Test
    fun customAppBarTest(){
        composeTestRule.setContent {
            CustomScreenBar{}
        }

        composeTestRule.onNodeWithText("custom mode", ignoreCase = true).assertIsDisplayed()
    }

}