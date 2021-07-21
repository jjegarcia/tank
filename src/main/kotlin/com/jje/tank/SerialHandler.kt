package com.jje.tank

import jssc.SerialPort

class SerialHandler {
    lateinit var serialPort: SerialPort
    fun serialPortInit(): SerialPort {
        serialPort = SerialPort("COM1")
        serialPort.openPort();//Open serial port
        serialPort.setParams(SerialPort.BAUDRATE_9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE) //Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
        return serialPort
    }

    @ExperimentalStdlibApi
     fun writeSerialString(inputBuffer: String) {
        writeSerialBytes(inputBuffer.encodeToByteArray())
    }

    fun readSerialString(byteCount: Int): String {
        return readSerialBytes(byteCount).toString()
    }

    fun writeSerialBytes(inputBuffer: ByteArray){
        serialPort.writeBytes(inputBuffer)
    }

    fun readSerialBytes(byteCount: Int): ByteArray? {
        return serialPort.readBytes()
    }
    fun closePort(){
        serialPort.closePort()
    }
}