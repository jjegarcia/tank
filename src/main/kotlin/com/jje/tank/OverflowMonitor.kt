package com.jje.tank

class OverflowMonitor(val repetitionLimit: Int, val differentialCalculator: DifferentialCalculator) {
    val levelHistory: MutableList<Int> = mutableListOf()
    private fun updateHistory(level: Int) {
        levelHistory.add(level)
        if (levelHistory.size > repetitionLimit) {
            levelHistory.removeAt(0) //FIFO
        }
    }

    fun isOverflowing(level: Int): Boolean {
        updateHistory(level)
        val matcher = levelHistory.filter {
            it > HIGH_LEVEL
        }
        return matcher.size ==
                levelHistory.size &&
                differentialCalculator.levelDifferential(
                        matcher,
                        Slope.INC,
                        repetitionLimit
                )
    }

    fun levelNotChanged(level: Int): Boolean {
        updateHistory(level)
        return levelHistory.toHashSet().size == 1 && levelHistory.size == repetitionLimit
    }

    companion object {

        enum class Slope {
            INC,
            DEC,
            EQU
        }
    }
}