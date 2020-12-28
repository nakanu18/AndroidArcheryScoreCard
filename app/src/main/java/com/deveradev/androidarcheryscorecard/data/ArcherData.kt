package com.deveradev.androidarcheryscorecard.data

data class ArcherData(
    val roundFormats: List<RoundFormat>,
    val rounds: List<Round>,
    val saveDate: String,
    val saveVersion: Int,
    val tags: List<Tag>
) {
    fun getRoundFormat(ID: Int): RoundFormat? {
        return this.roundFormats.find { it.ID == ID }
    }

    fun getRound(ID: Int): Round? {
        return this.rounds.find { it.ID == ID }
    }

    fun getTag(ID: Int): Tag? {
        return this.tags.find { it.ID == ID }
    }

    fun getScorecardForRound(round: Round): Scorecard {
        return Scorecard(round)
    }

    fun getArrow(round: Round, endID: Int, arrowID: Int): Int {
        val index = endID * round.roundFormat.arrowsPerEnd + arrowID
        return round.arrows[index]
    }

}

data class RoundFormat(
    val ID: Int,
    val arrowsPerEnd: Int,
    val distance: String,
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
                0 to 0, -1 to 0
            )

            return vegasScoreMap[arrow] ?: 0
        }
    }

}

data class Round(
    val ID: Int,
    val roundFormat: RoundFormat,
    val date: String,
    val arrows: List<Int>,
    val tags: List<Int>
)

data class Tag(
    val ID: Int,
    val name: String,
    val notes: String = ""
)
