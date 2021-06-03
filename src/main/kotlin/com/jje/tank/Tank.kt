package com.jje.tank

class Tank(var state: TankState, val inletValve: Valve, val outletValve: Valve) {
    fun highLevel() {
        if (state == TankState.FILLING) {
            state = TankState.FULL
            inletValve.close()
        }

    }

    fun flush() {
        if (state == TankState.FULL) {
            state = TankState.FLUSHING
            outletValve.open()
        }
    }

    fun lowLevel() {
        if (state == TankState.FLUSHING) {
            state = TankState.FILLING
            inletValve.open()
            outletValve.close()
        }
    }

}
