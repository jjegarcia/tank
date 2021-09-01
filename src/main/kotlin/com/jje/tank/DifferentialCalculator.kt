package com.jje.tank

class DifferentialCalculator {
   fun levelDifferential(matcher: List<Int>, slope: OverflowMonitor.Companion.Slope, repetitionLimit: Int): Boolean {
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

    private fun getDifferential(slope: OverflowMonitor.Companion.Slope, level: Int, previuos: Int): Int {
        val predicate = when (slope) {
            OverflowMonitor.Companion.Slope.INC -> level > previuos
            OverflowMonitor.Companion.Slope.DEC -> level < previuos
            OverflowMonitor.Companion.Slope.EQU -> level == previuos
        }
        return if (predicate) 1
        else 0
    }

}