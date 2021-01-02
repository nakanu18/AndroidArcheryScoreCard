package com.deveradev.androidarcheryscorecard.data

data class ArcherData(
    val roundFormats: ArrayList<RoundFormat>,
    val rounds: ArrayList<Round>,
    val saveDate: String,
    val saveVersion: Int,
    val tags: ArrayList<Tag>
) {
    companion object {
        fun createNewRound(
            rounds: ArrayList<Round>,
            roundFormats: ArrayList<RoundFormat>
        ): Round {
            val newRoundID = getNewIDForRounds(rounds)
            val roundFormat = roundFormats[0]
            return Round(
                newRoundID,
                roundFormat,
                "Today",
                ArrayList<Int>(),
                ArrayList<Int>())
                .apply {
                    setUpDefaultValuesForArrows()
                }
        }

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

        fun getArrowScore(round: Round, endID: Int, arrowID: Int): Int {
            val index = endID * round.roundFormat.arrowsPerEnd + arrowID
            return round.arrows[index]
        }

        // Finds the last unassigned arrowID in the end.  Returns -1 if all slots are filled.
        fun findFirstUnassignedArrowID(round: Round, endID: Int): Int {
            val endStartID = endID * round.roundFormat.arrowsPerEnd

            for (i in 0 until 3) {
                if (round.arrows[endStartID + i] == -1) {
                    return endStartID + i
                }
            }
            return -1
        }

        fun findLastAssignedArrowID(round: Round, endID: Int): Int {
            val endStartID = endID * round.roundFormat.arrowsPerEnd

            for (i in 2 downTo 0 step 1) {
                if (round.arrows[endStartID + i] != -1) {
                    return endStartID + i
                }
            }
            return -1
        }

        // Set the arrow score for the last missing value of this end
        fun setLastArrowScore(round: Round, endID: Int, arrowScore: Int): Boolean {
            val arrowID = findFirstUnassignedArrowID(round, endID)
            if (arrowID != -1) {
                round.arrows[arrowID] = arrowScore
                return true
            }
            return false
        }

        fun eraseLastArrowScore(round: Round, endID: Int): Boolean {
            val arrowID = findLastAssignedArrowID(round, endID)
            if (arrowID != -1) {
                round.arrows[arrowID] = -1
                return true
            }
            return false
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
) {
    fun setUpDefaultValuesForArrows() {
        if (this.arrows.size == 0) {
            for (i in 0..this.roundFormat.arrowsPerEnd * this.roundFormat.numEnds) {
                this.arrows.add(-1)
            }
        }
    }
}

data class Tag(
    val ID: Int,
    val name: String,
    val notes: String = ""
)
