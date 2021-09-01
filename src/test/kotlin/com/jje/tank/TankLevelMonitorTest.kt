package com.jje.tank

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

const val REPETITION_LIMIT = 3

class TankLevelMonitorTest {
    val tank = mockk<Tank>(relaxed = true)

    @Test
    fun `given Flushing when transition above to below low level then Low Level`() {
        val levelMonitor = setUpTankLevelMonitor(TankState.FLUSHING)
        levelMonitor.current(LOW_LEVEL + 1)
        levelMonitor.current(LOW_LEVEL)


        verify {
            tank.lowLevel()
        }
    }

    @Test
    fun `given Flushing when transition not met then nothing happens`() {
        val levelMonitor = setUpTankLevelMonitor(TankState.FLUSHING)
        levelMonitor.current(LOW_LEVEL + 2)
        levelMonitor.current(LOW_LEVEL + 1)


        verify(exactly = 0) {
            tank.lowLevel()
        }
    }

    @Test
    fun `given Filling when transition below to high level then high level`() {
        val levelMonitor = setUpTankLevelMonitor(TankState.FILLING)
        levelMonitor.current(HIGH_LEVEL - 1)
        levelMonitor.current(HIGH_LEVEL)

        verify {
            tank.highLevel()
        }
    }

    @Test
    fun `given Filling when no transition below to high level then remains in filling`() {
        val levelMonitor = setUpTankLevelMonitor(TankState.FILLING)
        levelMonitor.current(HIGH_LEVEL - 2)
        levelMonitor.current(HIGH_LEVEL - 1)

        verify(exactly = 0) {
            tank.highLevel()
        }
    }

    @Test
    fun `given Full when no transition  then remains in full`() {
        val levelMonitor = setUpTankLevelMonitor(TankState.FULL)
        levelMonitor.current(HIGH_LEVEL)

        verify(exactly = 0) {
            tank.lowLevel()
        }
        verify {
            tank.highLevel()
        }
    }

    @Test
    fun `given Flushing when no transition above to low level then remains in flushing`() {
        val levelMonitor = setUpTankLevelMonitor(TankState.FLUSHING)
        levelMonitor.current(HIGH_LEVEL - 1)
        levelMonitor.current(HIGH_LEVEL - 2)

        verify(exactly = 0) {
            tank.lowLevel()
        }
    }

    @Test
    fun `given Full when level is greater than current few times then overflow should be invoked`() {
        val levelMonitor = setUpTankLevelMonitor(TankState.FULL)
        levelMonitor.current(HIGH_LEVEL)
        levelMonitor.current(HIGH_LEVEL + 1)
        levelMonitor.current(HIGH_LEVEL + 2)
        levelMonitor.current(HIGH_LEVEL + 9)

        verify {
            tank.overflow()
        }
    }

    @Test
    fun `given Full when level is not greater than current few times then overflow should not be invoked`() {
        val levelMonitor = setUpTankLevelMonitor(TankState.FULL)
        levelMonitor.current(HIGH_LEVEL)
        levelMonitor.current(HIGH_LEVEL + 9)
        levelMonitor.current(HIGH_LEVEL + 2)
        levelMonitor.current(HIGH_LEVEL + 8)

        verify(exactly = 0) {
            tank.overflow()
        }
    }

    @Test
    fun `given Filling when level is not increasing then blocked inlet`() {
        val levelMonitor = setUpTankLevelMonitor(TankState.FILLING)
        levelMonitor.current(LOW_LEVEL)
        levelMonitor.current(LOW_LEVEL + 5)
        levelMonitor.current(LOW_LEVEL + 5)
        levelMonitor.current(LOW_LEVEL + 5)


        verify { tank.inletBlock() }
    }

    @Test
    fun `given Flushing when level is not decreasing then blocked outlet`() {
        val levelMonitor = setUpTankLevelMonitor(TankState.FLUSHING)
        levelMonitor.current((HIGH_LEVEL - 5))
        levelMonitor.current((HIGH_LEVEL - 5))
        levelMonitor.current((HIGH_LEVEL - 5))

        verify { tank.outletBlocked() }
    }

    private fun setUpTankLevelMonitor(tankState: TankState): TankLevelMonitor {
        every { tank.state } returns tankState

        val levelMonitor = TankLevelMonitor(tank, OverflowMonitor(REPETITION_LIMIT, DifferentialCalculator()))
        return levelMonitor
    }

    @Test
    fun `given Full when level is decreasing then tank leak`() {
        val levelMonitor=setUpTankLevelMonitor(TankState.FULL)
        levelMonitor.current(HIGH_LEVEL-4)
        levelMonitor.current(HIGH_LEVEL-5)
        levelMonitor.current(HIGH_LEVEL-6)

        verify { tank.leak() }
    }

}