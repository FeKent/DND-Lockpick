package com.fekent.dndlockpick.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
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

    fun NavController.assertCurrentRouteName(expectedRouteName: String){
        assertEquals(expectedRouteName, currentDestination?.route)
    }

    @Test
    fun navController_clickCustom_navigatesToCustomScreen(){
        composeTestRule.onNodeWithText("custom game", ignoreCase = true)
            .performClick()
        navController.assertCurrentRouteName(Screen.Custom.route)
    }
}