package com.jje.tank

import jssc.SerialPort

interface SerialInterface {
    fun serialPortInit(): SerialPort
    fun writeSerial(inputBuffer: String)
    fun readSerial(byteCount: Int): String
    fun writeSerialBytes(inputBuffer: ByteArray)
    fun readSerialBytes(byteCount: Int): ByteArray?
    fun closePort()
}

class SerialWrapper() : SerialInterface {
    val serialHandler = SerialHandler()
    override fun serialPortInit(): SerialPort {
        return serialHandler.serialPortInit()
    }

    @ExperimentalStdlibApi
    override fun writeSerial(inputBuffer: String) {
        serialHandler.writeSerialString(inputBuffer)
    }

    override fun readSerial(byteCount: Int): String {
        return serialHandler.readSerialString(byteCount)
    }

    override fun writeSerialBytes(inputBuffer: ByteArray) {
        serialHandler.writeSerialBytes(inputBuffer)
    }

    override fun readSerialBytes(byteCount: Int): ByteArray? {
        return serialHandler.readSerialBytes(byteCount)
    }

    override fun closePort() {
        serialHandler.closePort()
    }
}