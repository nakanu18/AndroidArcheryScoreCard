package com.deveradev.androidarcheryscorecard.data

import androidx.lifecycle.ViewModel

class RoundViewModel(val round: Round) : ViewModel() {

    val scorecard = ArcherData.getScorecardForRound(this.round)

    val roundName = this.round.roundFormat.name
    val date = this.round.date
    val distance = this.round.roundFormat.distance
    val score = "${this.scorecard.totalScore}"
    val scoreEx = "${this.scorecard.totalXCount}x"

}
