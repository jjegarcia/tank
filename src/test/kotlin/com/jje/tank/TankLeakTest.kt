package com.jje.tank

import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TankLeakTest {
    private val output: Output = mockk(relaxed = true)
    private lateinit var tank: Tank

    @Test
    fun `given tank full when low level then notify user`(){
        tank.lowLevel()

        verify {
            output.notify("tank leak")
        }
    }

    @Test
    fun `given tank full when low level then led is on`(){
        tank.lowLevel()

        verify {
            tank.led.switchOn()
        }
    }

    @Test
    fun `given tank full when low level then state is tank leak`(){
        tank.lowLevel()
        assertEquals(TankState.TANK_LEAK,tank.state)
       }

    @Test
    fun `given tank leak when reset then filling tank`(){
        tank.state=TankState.TANK_LEAK
        tank.reset()

        assertEquals(TankState.FILLING,tank.state)
     }

    @Test
    fun `given tank is FULL when reset then should not open inlet valve`(){
         tank.reset()

            verify(exactly = 0) {
                tank.inletValve.open()
            }
    }


    @Before
    fun setUp() {
        val inletValve = mockk<Valve>(relaxed = true)
        val outletValve = mockk<Valve>(relaxed = true)
        tank = Tank(state = TankState.FULL, inletValve = inletValve, outletValve = outletValve, output = output, led = mockk(relaxed = true))
    }
}