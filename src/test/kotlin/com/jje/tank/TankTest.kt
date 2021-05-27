package com.jje.tank

import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class TankTest {
    @Test
    fun givenTankIsFilling_WhenHighLevelReached_ThenTankIsFull(){
        val inletValve= mockk<InletValve>(relaxed = true)
        val tank = Tank(state=TankState.FILLING, inletValve = inletValve)
        tank.highLevel()
        assertEquals(TankState.FULL,tank.state)
    }

    @Test
    fun givenTankIsFilling_WhenHighLevelReached_ThenCloseInlet(){
        val inletValve= mockk<InletValve>(relaxed = true)

        val tank = Tank(inletValve=inletValve,state=TankState.FILLING)
        tank.highLevel()
        verify{
            inletValve.close()
        }
    }

}