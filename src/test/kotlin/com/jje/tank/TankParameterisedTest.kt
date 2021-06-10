package com.jje.tank

import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.reflect.KFunction1


@RunWith(value = Parameterized::class)
class TankParameterisedTest(val startState: TankState, val action: (Tank) -> Unit, val endState: TankState) {

    private val inletValve: Valve = mockk<Valve>(relaxed = true)
    private val outLetValve = mockk<Valve>(relaxed = true)

    @Test
    fun `after event tank ends in correct state`() {

        val tank = Tank(state = startState, inletValve = inletValve, outletValve = outLetValve)

        action(tank)
        assertEquals(endState, tank.state)
    }

    companion object {
        data class TestParameters(val startState: TankState, val method: (Tank) -> Unit, val endState: TankState) {
            val array: Array<Any>
                get() = arrayOf(startState, method, endState)
        }

        @get:Parameterized.Parameters
        @JvmStatic
        val parameters: List<Array<Any>>
            get() = listOf(TestParameters(TankState.FULL, Tank::flush, TankState.FLUSHING).array)
    }
}