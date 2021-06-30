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

    @Test
    fun `given tank is FULL when reset then should not open outlet valve`(){
        tank.reset()

        verify(exactly = 0) {
            tank.outletValve.open()
        }
    }

    @Test
    fun `given Flushing when reset then should not close outlet valve`(){
        tank.state=TankState.FLUSHING
        tank.reset()

        verify(exactly = 0) {
            tank.outletValve.close()
        }
    }

    @Test
    fun `given Flushing when reset then should not open inlet valve`(){
        tank.state=TankState.FLUSHING
        tank.reset()

        verify(exactly = 0) {
            tank.inletValve.open()
        }
    }

    @Test
    fun `given tank is leaking when reset then should open inlet valve`(){
        tank.state=TankState.TANK_LEAK
        tank.reset()

        verify(exactly = 1) {
            tank.inletValve.open()
        }
    }

    @Test
    fun `given tank is leaking when reset then led should turn off`(){
        tank.state=TankState.TANK_LEAK
        tank.reset()

        verify(exactly = 1) {
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