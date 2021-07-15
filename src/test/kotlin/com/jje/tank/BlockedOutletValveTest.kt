package com.jje.tank

import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class BlockedOutletValveTest {
    val inletValve = mockk<Valve>()
    val outletValve = mockk<Valve>(relaxed = true)
    val output = mockk<Output>(relaxed = true)
    val led = mockk<Led>(relaxed = true)

    @Test
    fun `given flushing when outlet is blocked then blocked outlet`() {
        val tank = Tank(TankState.FLUSHING, inletValve, outletValve, output, led)

        tank.outletBlocked()

        assertEquals(TankState.BLOCKED_OUTLET, tank.state)

    }

    @Test
    fun `given flushing when outlet is blocked then notifyUser`() {
        val tank = Tank(TankState.FLUSHING, inletValve, outletValve, output, led)

        tank.outletBlocked()

        verify {
            output.notify("Outlet Valve Blocked")
        }
    }

    @Test
    fun `given flushing when outlet is blocked then switch on led`() {
        val tank = Tank(TankState.FLUSHING, inletValve, outletValve, output, led)

        tank.outletBlocked()

        verify {
            led.switchOn()
        }
    }

    @Test
    fun `given flushing when outlet is blocked then close outlet valve`() {
        val tank = Tank(TankState.FLUSHING, inletValve, outletValve, output, led)

        tank.outletBlocked()

        verify {
            outletValve.close()
        }
    }

    @Test
    fun `given blocked outlet when reset then flushing`() {
        val tank = Tank(TankState.BLOCKED_OUTLET, inletValve, outletValve, output, led)

        tank.reset()

        assertEquals(TankState.FLUSHING,tank.state)
     }

    @Test
    fun `given blocked outlet when reset then switch off led`() {
        val tank = Tank(TankState.BLOCKED_OUTLET, inletValve, outletValve, output, led)

        tank.reset()

        verify {
            led.switchOff()
        }
    }

    @Test
    fun `given blocked outlet when reset then open outlet valve`() {
        val tank = Tank(TankState.BLOCKED_OUTLET, inletValve, outletValve, output, led)

        tank.reset()

        verify {
            outletValve.close()
        }
    }

}