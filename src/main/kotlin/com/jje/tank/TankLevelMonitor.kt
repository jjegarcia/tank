package com.jje.tank

const val LOW_LEVEL = 50
const val HIGH_LEVEL = 100
const val FLUSH_COMMAND = -1

class TankLevelMonitor(val tank: Tank, val overflowMonitor: OverflowMonitor) : LevelMonitor {
    override fun current(input: Int) {
        if (input == FLUSH_COMMAND) {
            tank.flush()
            return
        }
        analyseLevel(input)
    }

    private fun analyseLevel(level: Int) {
        if (level <= LOW_LEVEL) {
            tank.lowLevel()
        }
        if (level >= HIGH_LEVEL) {
            tank.highLevel()
        }
        if (overflowMonitor.isOverflowing(level)) {
            tank.overflow()
        }
        if (tank.state== TankState.FILLING && overflowMonitor.levelNotChanged(level)){
            tank.inletBlock()
        }
        if (tank.state== TankState.FLUSHING && overflowMonitor.levelNotChanged(level)) {
            tank.outletBlocked()
        }
        if (tank.state==TankState.FULL && overflowMonitor.levelDecreasing(level)){
            tank.leak()
        }
    }
}

