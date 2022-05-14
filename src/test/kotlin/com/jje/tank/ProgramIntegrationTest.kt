package com.jje.tank

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class ProgramIntegrationTest {
    val inletValve: Valve = mockk(relaxed = true)
    val outletValve: Valve = mockk(relaxed = true)
    val led: Led = mockk(relaxed = true)

    @Test
    fun `giving filling when digipot greater than High Level then close inlet valve`() {

        val program = createProgram(listOf<Int>(99, 104), TankState.FILLING)

        program.start()


        verify {
            inletValve.close()
        }
    }

    @Test
    fun `giving flush when digipot lower than Low Level then close outlet valve and open inlet valve`() {

        val program = createProgram(listOf<Int>(51, 44), TankState.FLUSHING)

        program.start()


        verify {
            outletValve.close()
        }
        verify {
            inletValve.open()
        }
    }

    @Test
    fun `giving full when digipot is -1 then outlet valve opens`() {
        val program = createProgram(listOf<Int>(-1), TankState.FULL)
        program.start()
        verify { outletValve.open() }
    }

    @Test
    fun `giving flushing when digipot decreases then open inlet and close outlet valves`() {

        val program = createProgram(
            listOf<Int>(
                LOW_LEVEL + 5,
                LOW_LEVEL - 1
            ), TankState.FLUSHING
        )


        program.start()

        verify {
            outletValve.close()
            inletValve.open()
        }
    }

    @Test
    fun `giving Filling when digipot no raised then notify user,switch led on, close inlet valve`() {
        val program = createProgram(
            listOf<Int>(
                LOW_LEVEL + 5,
                LOW_LEVEL + 5,
                LOW_LEVEL + 5,
                LOW_LEVEL + 5
            ), TankState.FILLING
        )

        program.start()

        verify {
            led.switchOn()
        }
    }

    private fun createProgram(digipotValues: List<Int>, tankState: TankState): Program {
        val timerHandler: TimerHandler = mockk(relaxed = true)
        val tank = Tank(tankState, inletValve, outletValve, mockk(relaxed = true), led)
        val serialInterface: SerialInterface = mockk(relaxed = true)
        every { serialInterface.readSerialBytes(1) } returnsMany digipotValues.map { digipotValue ->
            ByteArray(
                1,
                { digipotValue.toByte() })
        }
        val levelMonitor = TankLevelMonitor(tank, OverflowMonitor(3, DifferentialCalculator()))
        val process: Process = mockk(relaxed = true)
        val processCycles = digipotValues.map { true }.toMutableList()
        processCycles.add(false)
        every { process.running() } returnsMany (processCycles)
        return Program(serialInterface, timerHandler, levelMonitor, process)
    }
}