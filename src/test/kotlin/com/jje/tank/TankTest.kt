package com.jje.tank

import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class TankTest {
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
    fun `given tank is full when flush then Flushing`() {
        val tank = Tank(state = TankState.FULL, inletValve = inletValve, outletValve = outLetValve)

        tank.flush()

        assertEquals(TankState.FLUSHING, tank.state)
    }

    @Test
    fun `given tank is full when flush then open Outlet`() {

        val tank = Tank(state = TankState.FULL, inletValve = inletValve, outletValve = outLetValve)

        tank.flush()

        verify {
            outLetValve.open()
        }
    }

    @Test
    fun `given tank is flushing when level is low then filling`() {

        val tank = Tank(state = TankState.FLUSHING, inletValve = inletValve, outletValve = outLetValve)

        tank.lowLevel()

        assertEquals(TankState.FILLING, tank.state)
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
    fun `given tank is filling when flush then nothing happens`() {

        val tank = Tank(state = TankState.FILLING, inletValve = inletValve, outletValve = outLetValve)

        tank.flush()

        verify(exactly = 0) { outLetValve.open() }
    }

    @Test
    fun `given tank is full when low level then nothing happens`() {
        val tank = Tank(state = TankState.FULL, inletValve = inletValve, outletValve = outLetValve)

        tank.lowLevel()

        verify(exactly = 0) { inletValve.open() }
    }

    @Test
    fun `given tank is flushing when high level then nothing happens`() {
        val tank = Tank(state = TankState.FLUSHING, inletValve = inletValve, outletValve = outLetValve)

        tank.highLevel()

        assertEquals(TankState.FLUSHING,tank.state)
    }

}