package com.deveradev.androidarcheryscorecard.data

data class ArcherData(
    val roundFormats: List<RoundFormat>,
    val rounds: List<Round>,
    val saveDate: String,
    val saveVersion: Int,
    val tags: List<Tag>
) {
    fun getRoundFormat(ID: Int): RoundFormat? {
        return roundFormats.find { it.ID == ID }
    }

    fun getRound(ID: Int): Round? {
        return rounds.find { it.ID == ID}
    }

    fun getTag(ID: Int): Tag? {
        return tags.find { it.ID == ID }
    }

    fun getMaxScoreForRound(round: Round): Int {
        val roundFormat = getRoundFormat(round.roundFormatID)

        return roundFormat?.maxScore ?: 0
    }

    fun getScorecardForRound(round: Round): List<Int> {
        val endScores = mutableListOf<Int>()

        getRoundFormat(round.roundFormatID)?.let {
            var endScore = 0
            var j = 0

            for (i in 0 until (it.numEnds * it.arrowsPerEnd)) {
                endScore += RoundFormat.getVegasArrowScore(round.scores[i])

                if (++j == 3) {
                    endScores.add(endScore)
                    endScore = 0
                    j = 0
                }
            }
        }
        return endScores.toList()
    }
}

data class RoundFormat(
    val ID: Int,
    val arrowsPerEnd: Int,
    val name: String,
    val numEnds: Int,
    val maxScore: Int
) {

    companion object {
        fun getVegasArrowScore(arrow: Int): Int {
            val vegasScoreMap = mapOf(
                11 to 10, 10 to 10, 9 to 9,
                8 to 8, 7 to 7, 6 to 6, 5 to 5,
                4 to 4, 3 to 3, 2 to 2, 1 to 1,
                0 to 0, -1 to 0)

            return vegasScoreMap[arrow] ?: 0
        }
    }

}

data class Round(
    val ID: Int,
    val roundFormatID: Int,
    val date: String,
    val kit: Int,
    val scores: List<Int>,
    val tags: List<Int>
) {
    fun getTotalScore(): Int {
        return scores.fold(0) { acc, arrow ->
            RoundFormat.getVegasArrowScore(arrow) + acc
        }
    }
}

data class Tag(
    val ID: Int,
    val name: String,
    val notes: String = ""
)