package com.jje.tank

public class Main {
    var digipotValue: Int=0;
    @ExperimentalStdlibApi
    public fun main(args: Array<String>) {
        val serialWrapper= SerialWrapper()
        serialWrapper.serialPortInit()
        serialWrapper.closePort()//Close serial port
        }
}