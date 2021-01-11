package com.deveradev.androidarcheryscorecard.ui.roundeditor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.deveradev.androidarcheryscorecard.R
import com.deveradev.androidarcheryscorecard.data.ArcherData
import com.deveradev.androidarcheryscorecard.data.Round
import com.deveradev.androidarcheryscorecard.data.RoundFormat
import com.deveradev.androidarcheryscorecard.utils.Utils

class RoundViewModel(private val round: Round, application: Application) : AndroidViewModel(application) {

    // NOTE: no live data here.  HistoryViewModel has a LiveData(selectedRound) and we build this
    // view model off of that data

    val scorecard = ArcherData.getScorecardForRound(this.round)

    val roundName = this.round.roundFormat.name
    val date = Utils.getDisplayDate(this.round.date)
    val distance = this.round.roundFormat.distance
    val score = "${this.scorecard.totalScore}"
    val scoreEx = "${this.scorecard.totalXCount}x"

    fun endNum(endID: Int) = "${endID + 1}"
    fun arrowScore(endID: Int, arrowID: Int) =
        RoundFormat.getVegasArrowScoreString(this.scorecard.endScores[endID].scores[arrowID])

    fun endTotal(endID: Int) = "${this.scorecard.endScores[endID].endTotal}"
    fun xCount(endID: Int) = "${this.scorecard.endScores[endID].xCount}x"

    fun getVegasStyleForArrow(arrow: String): Int {
        return when (arrow) {
            "X", "10", "9" -> R.style.rounded_arrow_score_wa_gold
            "8", "7" -> R.style.rounded_arrow_score_wa_red
            "6", "5" -> R.style.rounded_arrow_score_wa_blue
            "4", "3" -> R.style.rounded_arrow_score_wa_black
            "2", "1", "0", "M" -> R.style.rounded_arrow_score_wa_white
            else -> R.style.rounded_arrow_score_wa_blank
        }
    }

}
