package com.deveradev.androidarcheryscorecard.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deveradev.androidarcheryscorecard.ui.UNKNOWN_VALUE

class RoundViewModel(private val archerData: ArcherData, val round: Round) : ViewModel() {

    private val roundFormat = archerData.getRoundFormat(round.roundFormatID)
    private val scorecard = archerData.getScorecardForRound(round)

    val roundName = this.roundFormat?.name ?: UNKNOWN_VALUE
    val date = this.round.date
    val distance = this.roundFormat?.distance ?: UNKNOWN_VALUE
    val score = "${this.scorecard?.totalScore ?: UNKNOWN_VALUE}"
    val scoreEx = "${this.scorecard?.totalXCount ?: UNKNOWN_VALUE}x"

}
