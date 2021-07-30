package com.jje.tank


class OutputWrapper(val serialWrapper: SerialInterface):Output{
    @ExperimentalStdlibApi
    override fun notify(message: String) {
        serialWrapper.writeSerial(message)
    }
}
