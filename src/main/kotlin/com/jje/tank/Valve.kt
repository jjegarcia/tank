package com.jje.tank

interface Valve {
    fun close()
    fun open()
}

class ValveWrapper() : Valve {
    var isClosed: Boolean = false
    override fun close() {
        isClosed = true
    }

    override fun open() {
        isClosed = false
    }
}