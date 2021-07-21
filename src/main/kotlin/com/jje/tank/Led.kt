package com.jje.tank

interface Led {
    fun switchOn()
    fun switchOff()
}

class LedWrapper : Led {
    var ledValue: Boolean = false
    override fun switchOn() {
        ledValue = true
    }

    override fun switchOff() {
        ledValue = false
    }
}