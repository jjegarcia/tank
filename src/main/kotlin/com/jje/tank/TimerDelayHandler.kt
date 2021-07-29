package com.jje.tank

import java.util.*
import kotlin.concurrent.schedule

interface TimerHandler {
    fun setTimer(miliseconds: Long)
    fun delay(miliseconds: Long)
}

class TimerDelayHandler : TimerHandler {
    var completed: Boolean = false
    override fun setTimer(miliseconds: Long) {
        Timer("SettingUp", false).schedule(miliseconds) {
            completed = true
        }
    }

    override fun delay(miliseconds: Long) {
        completed = false
        while (completed != true) {
            setTimer(miliseconds = miliseconds)
        }
    }
}