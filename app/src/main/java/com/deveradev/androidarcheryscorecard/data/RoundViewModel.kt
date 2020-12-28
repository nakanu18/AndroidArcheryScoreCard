package com.deveradev.androidarcheryscorecard.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deveradev.androidarcheryscorecard.ui.UNKNOWN_VALUE

class RoundViewModel(private val archerData: ArcherData, val round: Round) : ViewModel() {

    private val scorecard = this.archerData.getScorecardForRound(this.round)

    val roundName = this.round.roundFormat.name
    val date = this.round.date
    val distance = this.round.roundFormat.distance
    val score = "${this.scorecard.totalScore}"
    val scoreEx = "${this.scorecard.totalXCount}x"

}
