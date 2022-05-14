package com.jje.tank

class OverflowMonitor(val repetitionLimit: Int, val differentialCalculator: DifferentialCalculator) {
    val levelHistory: MutableList<Int> = mutableListOf()
    fun updateHistory(level: Int) {
        levelHistory.add(level)
        if (levelHistory.size > repetitionLimit) {
            levelHistory.removeAt(0) //FIFO
        }
    }

    fun isOverflowing(level: Int): Boolean {
//        updateHistory(level)
        val h = levelHistory.map { p -> p }
        val matcher = h.filter {
            it > HIGH_LEVEL
        }
        val a = differentialCalculator.levelDifferential(
            matcher,
            Slope.INC,
            repetitionLimit
        )
        return a
    }

    fun levelNotChanged(level: Int): Boolean {
//        updateHistory(level)
        return levelHistory.toHashSet().size == 1 && levelHistory.size == repetitionLimit
    }

    fun levelDecreasing(level: Int): Boolean {
//        updateHistory(level)
        val sortedHistory = levelHistory.sortedDescending()
        return sortedHistory == levelHistory && levelHistory.size > 1
    }

    companion object {

        enum class Slope {
            INC,
            DEC,
            EQU
        }
    }
}