package com.jje.tank

import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.reflect.KFunction1


@RunWith(value = Parameterized::class)
class TankParameterisedTest(
        private val parameters: TestParameters
) {

    private val inletValve: Valve = mockk<Valve>(relaxed = true)
    private val outLetValve = mockk<Valve>(relaxed = true)

    @Test
    fun `after event tank ends in correct state`() {

        val tank = Tank(state = parameters.startState, inletValve = inletValve, outletValve = outLetValve)

        parameters.action(tank)
        assertEquals(parameters.endState, tank.state)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "")
        fun getParameters(): List<TestParameters> {
            return listOf(
                    TestParameters(TankState.FILLING, Tank::flush, TankState.FILLING),
                    TestParameters(TankState.FILLING, Tank::lowLevel, TankState.FILLING),
                    TestParameters(TankState.FILLING, Tank::highLevel, TankState.FULL),
                    TestParameters(TankState.FULL, Tank::flush, TankState.FLUSHING),
                    TestParameters(TankState.FULL, Tank::lowLevel, TankState.FULL),
                    TestParameters(TankState.FULL, Tank::highLevel, TankState.FULL),
                    TestParameters(TankState.FLUSHING, Tank::flush, TankState.FLUSHING),
                    TestParameters(TankState.FLUSHING, Tank::lowLevel, TankState.FILLING),
                    TestParameters(TankState.FLUSHING, Tank::highLevel, TankState.FLUSHING)
            )
        }

        data class TestParameters(val startState: TankState, val action: (Tank) -> Unit, val endState: TankState)
    }
}