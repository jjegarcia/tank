package com.jje.tank

import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class HighLevelTest {
    private val inletValve: Valve = mockk<Valve>(relaxed = true)
    private val outLetValve = mockk<Valve>(relaxed = true)

    @Test
    fun `given Tank Is Filling When High Level Reached Then Tank Is Full`() {
        val tank = Tank(state = TankState.FILLING, inletValve = inletValve, outletValve = outLetValve)

        tank.highLevel()

        assertEquals(TankState.FULL, tank.state)
    }

    @Test
    fun `given Tank Is Filling When HighLevel Reached Then Close Inlet`() {
        val tank = Tank(state = TankState.FILLING, inletValve = inletValve, outletValve = outLetValve)

        tank.highLevel()

        verify {
            inletValve.close()
        }
    }

    @Test
    fun `given tank is flushing when high level then nothing happens`() {
        val tank = Tank(state = TankState.FLUSHING, inletValve = inletValve, outletValve = outLetValve)

        tank.highLevel()

        assertEquals(TankState.FLUSHING,tank.state)
    }
}