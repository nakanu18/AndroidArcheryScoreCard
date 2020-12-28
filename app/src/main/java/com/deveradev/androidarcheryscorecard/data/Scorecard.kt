package com.deveradev.androidarcheryscorecard.data

import android.util.Log
import com.deveradev.androidarcheryscorecard.ui.AED_LOG_TAG

class Scorecard(val round: Round) {
    val roundFormat = round.roundFormat
    val endScores = mutableListOf<EndScore>()
//    val totalScore: Int
//        get() = endScores.last().runningTotal
    val totalScore: Int
    val maxScore = roundFormat.maxScore
    val totalXCount: Int

    init {
        var runningTotal = 0
        var runningXCount = 0

        for (i in 0 until this.roundFormat.numEnds) {
            val endScore = EndScore()

            for (j in 0 until this.roundFormat.arrowsPerEnd) {
                val arrowID = i * this.roundFormat.arrowsPerEnd + j
                val arrowScore = RoundFormat.getVegasArrowScore(this.round.arrows[arrowID])

                if (this.round.arrows[arrowID] == 11) {
                    ++endScore.xCount
                    ++runningXCount
                }
                runningTotal += arrowScore
                endScore.sortedScores.add(arrowScore)
                endScore.endTotal += arrowScore
                endScore.runningTotal = runningTotal
            }
            this.endScores.add(endScore)
        }
        this.totalScore = runningTotal
        this.totalXCount = runningXCount
    }

    override fun toString(): String {
        var desc = "${this.roundFormat.name}\n"

        for (endScore in this.endScores) {
            desc += endScore.toString() + "\n"
        }
        return desc
    }

    inner class EndScore() {
        val sortedScores = mutableListOf<Int>()
        var endTotal = 0
        var runningTotal = 0
        var xCount = 0

        override fun toString(): String {
            return "scores ${this.sortedScores.toString()} = ${this.endTotal} => ${this.runningTotal} : ${this.xCount}x"
        }
    }

}
