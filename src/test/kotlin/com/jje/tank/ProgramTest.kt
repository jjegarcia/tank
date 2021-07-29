package com.jje.tank

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class ProgramTest {

    @Test
    fun `serial interface brings a new value level monitor_current is invoked`(){
        val digipotValue = 5
        val levelMonitor =mockk<LevelMonitor>(relaxed = true)
        val serialInterface= mockk<SerialInterface>(relaxed = true)
        val timerHandler = mockk<TimerHandler>(relaxed = true)
        val process= mockk<Process>(relaxed = true)
        val program=Program(serialInterface, timerHandler,levelMonitor,process)

        every { serialInterface.readSerialBytes(1) } returns ByteArray(1,{
            n -> digipotValue.toByte()
            })
        every {process.running()  } returnsMany listOf(
                true,
                false
        )

        program.start()

        verify {
            levelMonitor.current(digipotValue)
        }
    }
}