package com.jje.tank

interface Output {
    fun notify(message: String)
}

class OutputWrapper(val serialWrapper: SerialWrapper):Output{
    @ExperimentalStdlibApi
    override fun notify(message: String) {
        serialWrapper.writeSerial(message)
    }
}
