package com.jje.tank

interface TimerHandler {
    fun delay(miliseconds: Long)
}

class TimerDelayHandler : TimerHandler {

    override fun delay(miliseconds: Long) {
        Thread.sleep(miliseconds)
    }
}