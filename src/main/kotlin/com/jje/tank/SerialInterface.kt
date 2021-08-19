package com.jje.tank

import jssc.SerialPort

interface SerialInterface {
    fun serialPortInit(): SerialPort
    fun writeSerial(inputBuffer: String)
    fun writeSerialBytes(inputBuffer: ByteArray)
    fun readSerialBytes(byteCount: Int): ByteArray?
    fun closePort()
}

class SerialWrapper() : SerialInterface {
    lateinit var serialPort: SerialPort
    override fun serialPortInit(): SerialPort {
        serialPort = SerialPort("/dev/tty.usbmodem02691")
        serialPort.openPort();//Open serial port
        serialPort.setParams(SerialPort.BAUDRATE_9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE) //Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
        return serialPort
    }

    @ExperimentalStdlibApi
    override fun writeSerial(inputBuffer: String) {
        writeSerialBytes(inputBuffer.encodeToByteArray())
    }

    override fun writeSerialBytes(inputBuffer: ByteArray){
        serialPort.writeBytes(inputBuffer)
    }

    override fun readSerialBytes(byteCount: Int): ByteArray? {
        return serialPort.readBytes()
    }
    override fun closePort(){
        serialPort.closePort()
    }
}