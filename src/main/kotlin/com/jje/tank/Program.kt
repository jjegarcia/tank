package com.jje.tank

class Program(val serialWrapper: SerialInterface, val timerHandler: TimerHandler, val levelMonitor: LevelMonitor, val process: Process) {
    var digipotValue: Int = 0;

    fun start() {

        serialWrapper.serialPortInit()

        while (process.running()) {
            digipotValue = serialWrapper.readSerialBytes(1)?.get(0)?.toInt() ?: -100
             levelMonitor.current(digipotValue)
            timerHandler.delay(1000)
            println("digipot:$digipotValue")
        }
        serialWrapper.closePort()
    }
}