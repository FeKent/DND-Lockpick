package com.fekent.dndlockpick.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.fekent.dndlockpick.DNDLockpick
import com.fekent.dndlockpick.Screen
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DndLockpickNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            DNDLockpick(navController)
        }
    }

    @Test
    fun dndLockpickNavHost_verifyStartDestination(){
        assertEquals(Screen.Mode.route,navController.currentDestination?.route)
    }
}