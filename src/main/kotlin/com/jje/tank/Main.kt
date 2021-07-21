package com.jje.tank

public class Main {
    var digipotValue: Int=0;
    @ExperimentalStdlibApi
    public fun main(args: Array<String>) {
        val serialWrapper= SerialWrapper()

        val tank=Tank(
                state =  TankState.FILLING,
                inletValve = ValveWrapper(),
                outletValve = ValveWrapper(),
                output = OutputWrapper(serialWrapper = serialWrapper),
                led = LedWrapper()
        )

        val levelMonitor=LevelMonitor(tank,3)

        serialWrapper.serialPortInit()

        while (true){
            digipotValue= serialWrapper.readSerialBytes(1)?.get(0)?.toInt() ?: -1

        }

        serialWrapper.closePort()//Close serial port
        }
}