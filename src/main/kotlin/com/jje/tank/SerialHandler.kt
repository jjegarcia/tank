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
     fun writeSerial(inputBuffer: String) {
        serialPort.writeBytes(inputBuffer.encodeToByteArray())
    }

    fun readSerial(byteCount: Int): String {
        return serialPort.readBytes(byteCount).toString()
    }

    fun closePort(){
        serialPort.closePort()
    }
}