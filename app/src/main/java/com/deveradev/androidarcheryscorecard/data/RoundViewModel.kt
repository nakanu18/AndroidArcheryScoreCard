package com.deveradev.androidarcheryscorecard.data

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.deveradev.androidarcheryscorecard.R

class RoundViewModel(val round: Round, application: Application) : AndroidViewModel(application) {

    // NOTE: no live data here.  HistoryViewModel has a LiveData(selectedRound) and we build this
    // view model off of that data

    val scorecard = ArcherData.getScorecardForRound(this.round)

    val roundName = this.round.roundFormat.name
    val date = this.round.date
    val distance = this.round.roundFormat.distance
    val score = "${this.scorecard.totalScore}"
    val scoreEx = "${this.scorecard.totalXCount}x"

    fun endNum(endID: Int) = "${endID + 1}"
    fun arrowScore(endID: Int, arrowID: Int) =
        RoundFormat.getVegasArrowScoreString(this.scorecard.endScores[endID].scores[arrowID])

    fun endTotal(endID: Int) = "${this.scorecard.endScores[endID].endTotal}"
    fun xCount(endID: Int) = "${this.scorecard.endScores[endID].xCount}x"

    fun getVegasTextColorForArrow(arrow: String): Int {
        val colorID = when (arrow) {
            "X", "10", "9", "2", "1", "M" -> R.color.wa_black
            "8", "7", "6", "5", "4", "3"  -> R.color.wa_white
            else -> R.color.wa_blank
        }

        return ContextCompat.getColor(getApplication(), colorID)
    }

    fun getVegasColorForArrow(arrow: String): Int {
        val colorID = when (arrow) {
            "X", "10", "9" -> R.color.wa_gold
            "8", "7" -> R.color.wa_red
            "6", "5" -> R.color.wa_blue
            "4", "3" -> R.color.wa_black
            "2", "1", "0", "M" -> R.color.wa_white
            else -> R.color.wa_blank
        }

        return ContextCompat.getColor(getApplication(), colorID)
    }

}
