package com.jje.tank

import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BlockedInletValveTest {
    private val output: Output = mockk(relaxed = true)
    private lateinit var tank: Tank

    @Test
    fun `inlet Valve blocked then notify user`() {
        tank.inletBlock()

        verify {
            output.notify("InletValve: Blocked")
        }
    }

    @Test
    fun `inlet Valve blocked then close inlet valve`() {
        tank.inletBlock()

        verify {
            tank.inletValve.close()
        }
    }

    @Test
    fun `inlet Valve blocked then blocked state`() {
        tank.inletBlock()
        assertEquals(TankState.BLOCKED_INLET, tank.state)

    }

    @Test
    fun `inlet Valve blocked then fail led is switched on`(){

        tank.inletBlock()
        verify { tank.led.switchOn() }
    }

    @Test
    fun `given blocked when reset then filling`() {
        tank.inletBlock()
        tank.reset()
        assertEquals(TankState.FILLING, tank.state)
    }

    @Test
    fun `given blocked when reset then open inlet valve`() {
        tank.inletBlock()
        tank.reset()

        verify {
            tank.inletValve.open()
        }
    }

    @Test
    fun `given blocked when reset then led is switched off`(){
        tank.inletBlock()
        tank.reset()
        verify { tank.led.switchOff() }
    }


    @Before
    fun setUp() {
        val inletValve = mockk<Valve>(relaxed = true)
        val outletValve = mockk<Valve>(relaxed = true)
        tank = Tank(state = TankState.FILLING, inletValve = inletValve, outletValve = outletValve, output = output, led = mockk(relaxed = true))
    }


}