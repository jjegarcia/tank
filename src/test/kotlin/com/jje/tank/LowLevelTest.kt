package com.jje.tank

import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class LowLevelTest {
    private val inletValve: Valve = mockk<Valve>(relaxed = true)
    private val outLetValve = mockk<Valve>(relaxed = true)

    @Test
    fun `given tank is flushing when level is low then filling`() {

        val tank = Tank(state = TankState.FLUSHING, inletValve = inletValve, outletValve = outLetValve)

        tank.lowLevel()

        Assert.assertEquals(TankState.FILLING, tank.state)
    }

    @Test
    fun `given tank is flushing when level is low then inlet valve open`() {
        val tank = Tank(state = TankState.FLUSHING, inletValve = inletValve, outletValve = outLetValve)

        tank.lowLevel()

        verify { inletValve.open() }
    }

    @Test
    fun `given tank is flushing when level is low then outlet valve closed`() {
        val tank = Tank(state = TankState.FLUSHING, inletValve = inletValve, outletValve = outLetValve)

        tank.lowLevel()

        verify { outLetValve.close() }
    }

    @Test
    fun `given tank is full when low level then nothing happens`() {
        val tank = Tank(state = TankState.FULL, inletValve = inletValve, outletValve = outLetValve)

        tank.lowLevel()

        verify(exactly = 0) { inletValve.open() }
    }
}