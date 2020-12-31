package com.deveradev.androidarcheryscorecard.data

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
                endScore.scores.add(this.round.arrows[arrowID])
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
        val scores = mutableListOf<Int>()
        var endTotal = 0
        var runningTotal = 0
        var xCount = 0

        override fun toString(): String {
            val scoreStrings = mutableListOf<String>()

            for (score in scores) {
                scoreStrings.add(RoundFormat.getVegasArrowScoreString(score))
            }

            return "scores $scoreStrings = ${this.endTotal} => ${this.runningTotal} : ${this.xCount}x"
        }
    }

}
