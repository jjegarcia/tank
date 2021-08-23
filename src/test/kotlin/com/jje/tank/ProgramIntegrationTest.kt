package com.jje.tank

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class ProgramIntegrationTest {

    @Test
    fun `giving filling when digipot greater than High Level then close inlet valve`() {

        val (inletValve: Valve, outletValve: Valve, program) = createProgram(listOf<Int>( 99,104), TankState.FILLING)

        program.start()


        verify {
            inletValve.close()
        }
    }

    @Test
    fun `giving flush when digipot lower than Low Level then close outlet valve and open inlet valve`() {

        val (inletValve: Valve, outletValve: Valve, program) = createProgram(listOf<Int>( 51,44), TankState.FLUSHING)

        program.start()


        verify {
            outletValve.close()
        }
        verify {
            inletValve.open()
        }
    }

    @Test
    fun `giving full when digipot is -1 then outlet valve opens`(){
        val (inletValve: Valve, outletValve: Valve, program) = createProgram(listOf<Int>( -1), TankState.FULL)
        program.start()
        verify { outletValve.open() }
    }

    private fun createProgram(digipotValues: List<Int>, tankState: TankState): Triple<Valve, Valve, Program> {
        val inletValve: Valve = mockk(relaxed = true)
        val timerHandler: TimerHandler = mockk(relaxed = true)
        val outletValve: Valve = mockk(relaxed = true)
        val tank = Tank(tankState, inletValve, outletValve, mockk(), mockk())
        val serialInterface: SerialInterface = mockk(relaxed = true)
        every { serialInterface.readSerialBytes(1) } returnsMany digipotValues.map { digipotValue -> ByteArray(1, { digipotValue.toByte() }) }
        val levelMonitor = TankLevelMonitor(tank, OverflowMonitor(3))
        val process: Process = mockk(relaxed = true)
        every { process.running() } returnsMany listOf<Boolean>(
                true,
                true,
                false
        )

        val program = Program(serialInterface, timerHandler, levelMonitor, process)
        return Triple(inletValve, outletValve, program)
    }
}