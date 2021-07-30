package com.jje.tank

class TankLed : Led {
    var ledValue: Boolean = false
    override fun switchOn() {
        ledValue = true
    }

    override fun switchOff() {
        ledValue = false
    }
}