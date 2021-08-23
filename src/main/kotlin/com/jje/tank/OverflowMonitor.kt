package com.jje.tank

class OverflowMonitor(val repetitionLimit: Int) {
    val levelHistory: MutableList<Int> = mutableListOf()
    val stateHistory: MutableList<TankState> = mutableListOf()
    private fun levelDifferential(matcher: List<Int>, slope: Slope): Boolean {
        if (matcher.size == repetitionLimit) {
            var differential = 0
            var previous = matcher[0]
            matcher.forEachIndexed { index, level ->
                if (index > 0) {
                    differential += getDifferential(slope, level, previous)
                    previous = level
                }
            }
            return differential == repetitionLimit - 1
        }
        return false
    }

    private fun getDifferential(slope: Slope, level: Int, previuos: Int): Int {
        val predicate = when (slope) {
            Slope.INC -> level > previuos
            Slope.DEC -> level < previuos
            Slope.EQU -> level == previuos
        }
        return if (predicate) 1
        else 0
    }

    private fun updateHistory(state: TankState, level: Int) {
        addHistory(
                stateFunction = { addStateHistory(stateHistory, state) },
                levelFunction = { addLevelHistory(levelHistory, level) }
        )
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

   fun detectOverflow(level: Int, tank: Tank) {
        updateHistory(tank.state, level)
        val matcher = levelHistory.filter {
            it > HIGH_LEVEL
        }
        if (matcher.size == stateHistory.size && levelDifferential(matcher = matcher, slope = OverflowMonitor.Companion.Slope.INC)) {
            tank.overflow()
        }
    }

    companion object {

        enum class Slope {
            INC,
            DEC,
            EQU
        }
    }
}