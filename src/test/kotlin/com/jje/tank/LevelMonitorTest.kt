package com.jje.tank

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

const val REPETITION_LIMIT = 3

class LevelMonitorTest {

    @Test
    fun `given Flushing when transition above to below low level then Low Level`() {
        val tank = mockk<Tank>(relaxed = true)
        every { tank.state } returns TankState.FLUSHING
        val levelMonitor = LevelMonitor(tank, REPETITION_LIMIT)
        levelMonitor.current(LOW_LEVEL + 1)
        levelMonitor.current(LOW_LEVEL)


        verify {
            tank.lowLevel()
        }
    }

    @Test
    fun `given Flushing when transition not met then nothing happens`() {
        val tank = mockk<Tank>(relaxed = true)
        every { tank.state } returns TankState.FLUSHING
        val levelMonitor = LevelMonitor(tank, REPETITION_LIMIT)
        levelMonitor.current(LOW_LEVEL + 2)
        levelMonitor.current(LOW_LEVEL + 1)


        verify(exactly = 0) {
            tank.lowLevel()
        }
    }

    @Test
    fun `given Filling when transition below to high level then high level`() {

        val tank = mockk<Tank>(relaxed = true)
        every { tank.state } returns TankState.FILLING
        val levelMonitor = LevelMonitor(tank, REPETITION_LIMIT)
        levelMonitor.current(HIGH_LEVEL - 1)
        levelMonitor.current(HIGH_LEVEL)

        verify {
            tank.highLevel()
        }
    }

    @Test
    fun `given Filling when no transition below to high level then remains in filling`() {

        val tank = mockk<Tank>(relaxed = true)
        every { tank.state } returns TankState.FILLING
        val levelMonitor = LevelMonitor(tank, REPETITION_LIMIT)
        levelMonitor.current(HIGH_LEVEL - 2)
        levelMonitor.current(HIGH_LEVEL - 1)

        verify(exactly = 0) {
            tank.highLevel()
        }
    }

    @Test
    fun `given Full when no transition  then remains in full`() {
        val tank = mockk<Tank>(relaxed = true)
        every { tank.state } returns TankState.FULL
        val levelMonitor = LevelMonitor(tank, REPETITION_LIMIT)
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
        val tank = mockk<Tank>(relaxed = true)
        every { tank.state } returns TankState.FLUSHING
        val levelMonitor = LevelMonitor(tank, REPETITION_LIMIT)
        levelMonitor.current(HIGH_LEVEL - 1)
        levelMonitor.current(HIGH_LEVEL - 2)

        verify(exactly = 0) {
            tank.lowLevel()
        }
    }

    @Test
    fun `given Full when level is greater than current few times then overflow should be invoked`() {
        val tank = mockk<Tank>(relaxed = true)
        every { tank.state } returns TankState.FULL
        val levelMonitor = LevelMonitor(tank, REPETITION_LIMIT)
        levelMonitor.current(HIGH_LEVEL)
        levelMonitor.current(HIGH_LEVEL+1)
        levelMonitor.current(HIGH_LEVEL+2)
        levelMonitor.current(HIGH_LEVEL+9)

        verify {
            tank.overflow()
        }
    }
    @Test
    fun `given Full when level is not greater than current few times then overflow should be invoked`() {
        val tank = mockk<Tank>(relaxed = true)
        every { tank.state } returns TankState.FULL
        val levelMonitor = LevelMonitor(tank, REPETITION_LIMIT)
        levelMonitor.current(HIGH_LEVEL)
        levelMonitor.current(HIGH_LEVEL+9)
        levelMonitor.current(HIGH_LEVEL+2)
        levelMonitor.current(HIGH_LEVEL+8)

        verify(exactly = 0){
            tank.overflow()
        }
    }

}