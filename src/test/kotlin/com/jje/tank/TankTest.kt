package com.jje.tank

import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class TankTest {
    @Test
    fun `given Tank Is Filling When High Level Reached Then Tank Is Full`(){
        val inletValve= mockk<InletValve>(relaxed = true)
        val tank = Tank(state=TankState.FILLING, inletValve = inletValve)

        tank.highLevel()

        assertEquals(TankState.FULL,tank.state)
    }

    @Test
    fun `given Tank Is Filling When HighLevel Reached Then CloseInlet`(){
        val inletValve= mockk<InletValve>(relaxed = true)
        val tank = Tank(inletValve=inletValve,state=TankState.FILLING)

        tank.highLevel()

        verify{
            inletValve.close()
        }
    }

    @Test
    fun `given tank is full when flush then Flushing`(){
        val dummyState = mockk<InletValve>(relaxed = true)
        val tank = Tank(state = TankState.FULL,inletValve = dummyState)

        tank.flush()

        assertEquals(TankState.FLUSHING,tank.state)
    }
}