package com.deveradev.androidarcheryscorecard.data

import androidx.lifecycle.ViewModel
import com.deveradev.androidarcheryscorecard.ui.UNKNOWN_VALUE

class RoundViewModel(private val archerData: ArcherData, private val round: Round) : ViewModel() {

    private val roundFormat = archerData.getRoundFormat(round.roundFormatID)
    private val scorecard = archerData.getScorecardForRound(round)

    val roundName = roundFormat?.name ?: UNKNOWN_VALUE
    val date = round.date
    val distance = roundFormat?.distance ?: UNKNOWN_VALUE
    val score = "${scorecard?.totalScore ?: UNKNOWN_VALUE}"
    val scoreEx = "${scorecard?.totalXCount ?: UNKNOWN_VALUE}x"

}
