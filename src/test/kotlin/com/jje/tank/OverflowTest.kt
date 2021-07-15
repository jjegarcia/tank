package com.jje.tank

import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class OverflowTest {
    private val output: Output = mockk(relaxed = true)
    private val inletValve: Valve = mockk(relaxed = true)
    private val outletValve: Valve = mockk(relaxed = true)
    private val led: Led = mockk(relaxed = true)
    private lateinit var tank: Tank

    @Test
    fun `given Full when Overflow then inletLeak`() {
        tank = Tank(TankState.FULL, inletValve, outletValve, output, led)

        tank.overflow()

        assertEquals(TankState.INLET_LEAK,tank.state)

    }

    @Test
    fun `given Full when Overflow then notify user`() {
        tank = Tank(TankState.FULL, inletValve, outletValve, output, led)

        tank.overflow()

        verify {
            output.notify("Overflow: Check inlet valve")
        }
    }

    @Test
    fun `given Full when Overflow then switch on led`() {
        tank = Tank(TankState.FULL, inletValve, outletValve, output, led)

        tank.overflow()

        verify {
            led.switchOn()
        }
    }

    @Test
    fun `given Inlet Leak when Reset then switch off led`() {
        tank = Tank(TankState.INLET_LEAK, inletValve, outletValve, output, led)

        tank.reset()

        verify {
            led.switchOff()
        }
    }

    @Test
    fun `given Inlet Leak when Reset then Full`() {
        tank = Tank(TankState.INLET_LEAK, inletValve, outletValve, output, led)

        tank.reset()

        assertEquals(TankState.FULL,tank.state)

     }
}