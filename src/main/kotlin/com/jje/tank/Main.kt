package com.jje.tank


fun main(args: Array<String>) {
    val serialWrapper = SerialWrapper()

    val tank = Tank(
            state = TankState.FILLING,
            inletValve = ValveWrapper(),
            outletValve = ValveWrapper(),
            output = OutputWrapper(serialWrapper = serialWrapper),
            led = LedWrapper()
    )
    Program(serialWrapper, TimerDelayHandler(), TankLevelMonitor(tank, 3), MultithreadedProcess()).start()
}