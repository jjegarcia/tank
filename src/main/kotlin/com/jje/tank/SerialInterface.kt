package com.jje.tank

import jssc.SerialPort

interface SerialInterface {
    fun serialPortInit(): SerialPort
    fun writeSerial(inputBuffer: String)
    fun readSerial(byteCount: Int): String
    fun closePort()
}

class SerialWrapper() : SerialInterface {
    val serialHandler = SerialHandler()
    override fun serialPortInit(): SerialPort {
        return serialHandler.serialPortInit()
    }

    @ExperimentalStdlibApi
    override fun writeSerial(inputBuffer: String) {
        serialHandler.writeSerial(inputBuffer)
    }

    override fun readSerial(byteCount: Int): String {
        return serialHandler.readSerial(byteCount)
    }

    override fun closePort() {
        serialHandler.closePort()
    }
}