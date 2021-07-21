package com.jje.tank

public class Main {
    @ExperimentalStdlibApi
    public fun main(args: Array<String>) {
        val serialWrapper= SerialWrapper()
        serialWrapper.writeSerial("test1")
        serialWrapper.closePort()//Close serial port
        }
}