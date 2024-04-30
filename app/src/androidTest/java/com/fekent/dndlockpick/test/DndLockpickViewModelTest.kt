
package com.fekent.dndlockpick.test

import com.fekent.dndlockpick.viewmodel.CustomViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class DndLockpickViewModelTest {
    private val viewModel = CustomViewModel()

    @Test
    fun customViewModel_UiStateUpdatesCorrectly(){
        viewModel.tumblerCount.value = 14
        viewModel.timeLimit.value = 15


        assertNotEquals(12, viewModel.timeLimit.value)
        assertEquals(14, viewModel.tumblerCount.value)

    }


}

