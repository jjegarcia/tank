package com.jje.tank

class Tank(var state: TankState, val inletValve: Valve, val outletValve: Valve) {
    fun highLevel() {
        state = TankState.FULL
        inletValve.close()
    }

    fun flush() {
        state = TankState.FLUSHING
        outletValve.open()
    }

    fun lowLevel() {
        state = TankState.FILLING
        inletValve.open()
        outletValve.close()
    }

}
