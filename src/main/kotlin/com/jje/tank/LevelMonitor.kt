package com.jje.tank

const val LOW_LEVEL = 100
const val HIGH_LEVEL = 1000

class LevelMonitor(val tank: Tank, repetitionLimit: Int) {
    fun current(value: Int) {
        if (value <= LOW_LEVEL) {
            tank.lowLevel()
        }
        if (value >= HIGH_LEVEL){
            tank.highLevel()
        }
    }
}
