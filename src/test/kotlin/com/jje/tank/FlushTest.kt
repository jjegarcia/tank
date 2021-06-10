package com.jje.tank

import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class FlushTest{
    private val inletValve: Valve = mockk<Valve>(relaxed = true)
    private val outLetValve = mockk<Valve>(relaxed = true)

    @Test
    fun `given tank is full when flush then Flushing`() {
        val tank = Tank(state = TankState.FULL, inletValve = inletValve, outletValve = outLetValve)

        tank.flush()

        Assert.assertEquals(TankState.FLUSHING, tank.state)
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
    fun `given tank is filling when flush then nothing happens`() {

        val tank = Tank(state = TankState.FILLING, inletValve = inletValve, outletValve = outLetValve)

        tank.flush()

        verify(exactly = 0) { outLetValve.open() }
    }
}