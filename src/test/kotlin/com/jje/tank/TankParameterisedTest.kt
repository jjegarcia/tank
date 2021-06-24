package com.jje.tank

import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


@RunWith(value = Parameterized::class)
class TankParameterisedTest(
        private val parameters: TestParameters
) {

    private val inletValve: Valve = mockk<Valve>(relaxed = true)
    private val outLetValve = mockk<Valve>(relaxed = true)
    private val output= mockk<Output>(relaxed = true)
    @Test
    fun `after event tank ends in correct state`() {

        val tank = Tank(state = parameters.startState, inletValve = inletValve, outletValve = outLetValve, output =output, led = mockk(relaxed = true))

        parameters.action(tank)
        assertEquals(parameters.endState, tank.state)
    }

    @Test
    fun `after event tank send valve request `() {

        val tank = Tank(state = parameters.startState, inletValve = inletValve, outletValve = outLetValve, output =output, led = mockk(relaxed = true))

        val valveRequest: ValveRequest = parameters.valveRequest

        parameters.action(tank)
        verify(exactly = valveRequest.inletValveOpen) { inletValve.open() }
        verify(exactly = valveRequest.inletValveClosed) { inletValve.close() }
        verify(exactly = valveRequest.outletValveOpen) { outLetValve.open() }
        verify(exactly = valveRequest.outletValveClosed) { outLetValve.close() }
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParameters(): List<TestParameters> {
            return listOf(
                    TestParameters(TankState.FILLING, Tank::flush, TankState.FILLING, ValveRequest(0, 0, 0, 0)),
                    TestParameters(TankState.FILLING, Tank::lowLevel, TankState.FILLING, ValveRequest(0, 0, 0, 0)),
                    TestParameters(TankState.FILLING, Tank::highLevel, TankState.FULL, ValveRequest(0, 1, 0, 0)),
                    TestParameters(TankState.FULL, Tank::flush, TankState.FLUSHING, ValveRequest(0, 0, 1, 0)),
                    TestParameters(TankState.FULL, Tank::lowLevel, TankState.TANK_LEAK, ValveRequest(0, 0, 0, 0)),
                    TestParameters(TankState.FULL, Tank::highLevel, TankState.FULL, ValveRequest(0, 0, 0, 0)),
                    TestParameters(TankState.FLUSHING, Tank::flush, TankState.FLUSHING, ValveRequest(0, 0, 0, 0)),
                    TestParameters(TankState.FLUSHING, Tank::lowLevel, TankState.FILLING, ValveRequest(1, 0, 0, 1)),
                    TestParameters(TankState.FLUSHING, Tank::highLevel, TankState.FLUSHING, ValveRequest(0, 0, 0, 0))
            )
        }

        data class TestParameters(val startState: TankState, val action: (Tank) -> Unit, val endState: TankState, val valveRequest: ValveRequest)
        data class ValveRequest(val inletValveOpen: Int, val inletValveClosed: Int, val outletValveOpen: Int, val outletValveClosed: Int)
    }
}