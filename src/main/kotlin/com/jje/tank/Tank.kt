package com.jje.tank

class Tank(var state: TankState, val inletValve: Valve, val outletValve: Valve, val output: Output, val led: Led) {
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
        if (state == TankState.FULL) {
            state = TankState.TANK_LEAK
            output.notify("tank leak")
            led.switchOn()
        }
    }

    fun inletBlock() {
        inletValve.close()
        output.notify("InletValve: Blocked")
        state = TankState.BLOCKED_INLET
        led.switchOn()
    }

    fun reset() {
        if (state == TankState.BLOCKED_INLET || state == TankState.TANK_LEAK) {
            inletValve.open()
            state = TankState.FILLING
            led.switchOff()
        }
        if(state==TankState.BLOCKED_OUTLET){
            state= TankState.FLUSHING
            led.switchOff()
            outletValve.close()
        }
    }

    fun outletBlocked() {
        state=TankState.BLOCKED_OUTLET
        output.notify("Outlet Valve Blocked")
        led.switchOn()
        outletValve.close()
    }
}
