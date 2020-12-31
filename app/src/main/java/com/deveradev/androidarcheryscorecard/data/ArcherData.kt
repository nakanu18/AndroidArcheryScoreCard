package com.deveradev.androidarcheryscorecard.data

data class ArcherData(
    val roundFormats: List<RoundFormat>,
    val rounds: List<Round>,
    val saveDate: String,
    val saveVersion: Int,
    val tags: List<Tag>
) {
    companion object {
        fun getScorecardForRound(round: Round): Scorecard {
            return Scorecard(round)
        }

        fun getArrow(round: Round, endID: Int, arrowID: Int): Int {
            val index = endID * round.roundFormat.arrowsPerEnd + arrowID
            return round.arrows[index]
        }
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
            return when (arrow) {
                11 -> 10
                -1 -> 0
                else -> arrow
            }
        }

        fun getVegasArrowScoreString(arrow: Int): String {
            return when (arrow) {
                11 -> "X"
                0 -> "M"
                -1 -> ""
                else -> arrow.toString()
            }
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
