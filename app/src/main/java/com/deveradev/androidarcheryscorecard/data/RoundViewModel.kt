package com.deveradev.androidarcheryscorecard.data

import androidx.lifecycle.ViewModel

class RoundViewModel(private val round: Round) : ViewModel() {

    val roundID = round.ID
    val recordID = round.ID
    val score = round.getTotalScore()

}
