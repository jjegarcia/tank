package com.jje.tank

const val LOW_LEVEL = 100
const val HIGH_LEVEL = 1000

class LevelMonitor(val tank: Tank, val repetitionLimit: Int) {
    val levelHistory: MutableList<Int> = mutableListOf()
    val stateHistory: MutableList<TankState> = mutableListOf()
    fun current(level: Int) {
        if (level <= LOW_LEVEL) {
            tank.lowLevel()
        }
        if (level >= HIGH_LEVEL) {
            tank.highLevel()
        }
        updateHistory(tank.state, level)
        val matcher = levelHistory.filter { level ->
            level > HIGH_LEVEL
        }
        if (matcher.size == stateHistory.size) {
            tank.overflow()
        }
    }

    private fun updateHistory(state: TankState, level: Int) {
        addHistory(
                stateFunction = { addStateHistory(stateHistory, state) },
                levelFunction = { addLevelHistory(levelHistory, level) }
        )
    }

    private fun addHistory(stateFunction: () -> Unit, levelFunction: () -> Unit, historyType: HistoryType) {
        when (historyType) {
            HistoryType.STATE -> stateFunction()
            else -> levelFunction()
        }
    }

    private fun addHistory(stateFunction: () -> Unit, levelFunction: () -> Unit) {
        stateFunction.invoke()
        levelFunction.invoke()
    }

    private fun addStateHistory(historyList: MutableList<TankState>, state: TankState) {
        historyList.add(state)
        if (historyList.size > repetitionLimit) {
            historyList.removeAt(0)
        }
    }

    private fun addLevelHistory(historyList: MutableList<Int>, level: Int) {
        historyList.add(level)
        if (historyList.size > repetitionLimit) {
            historyList.removeAt(0) //FIFO
        }
    }

    private fun wipeStateHistory() {
        stateHistory.removeAll { true }
    }

    private fun wipeLevelHistory() {
        levelHistory.removeAll { true }
    }

    companion object {
        enum class HistoryType {
            STATE,
        }
    }
}

