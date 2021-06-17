package com.jje.tank

import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class BlockedValveTest {

    @Test
    fun `inlet Valve blocked then notify user`() {
        val inletValve = mockk<Valve>(relaxed = true)
        val outletValve = mockk<Valve>(relaxed = true)
        val output: Output = mockk(relaxed = true)
        val tank = Tank(state = TankState.FILLING, inletValve = inletValve, outletValve = outletValve, output = output)

        tank.inletBlock()

        verify {
            output.notify("InletValve: Blocked")
        }
    }

}