package com.deveradev.androidarcheryscorecard.data

data class ArcherData(
    val roundFormats: ArrayList<RoundFormat>,
    val rounds: ArrayList<Round>,
    val saveDate: String,
    val saveVersion: Int,
    val tags: ArrayList<Tag>
) {
    companion object {
        fun getNewIDForRoundFormats(roundFormats: ArrayList<RoundFormat>): Int {
            return roundFormats.maxOf { it.ID } + 1
        }

        fun getNewIDForRounds(rounds: ArrayList<Round>): Int {
            return rounds.maxOf { it.ID } + 1
        }

        fun getNewIDForTags(tags: ArrayList<Tag>): Int {
            return tags.maxOf { it.ID } + 1
        }

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
    val arrows: ArrayList<Int>,
    val tags: ArrayList<Int>
)

data class Tag(
    val ID: Int,
    val name: String,
    val notes: String = ""
)
