package com.jje.tank

class Tank(var state: TankState, val inletValve: InletValve) {
    fun highLevel() {
    state=TankState.FULL
        inletValve.close()
    }

}
