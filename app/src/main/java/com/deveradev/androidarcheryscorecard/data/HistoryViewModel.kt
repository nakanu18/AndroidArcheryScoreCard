package com.deveradev.androidarcheryscorecard.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel(context: Context) : ViewModel() {

    private val archerData = ArcherDataRepository.getData(context)

    val rounds = MutableLiveData<ArrayList<RoundViewModel>>()

    init {
        rounds.value = ArrayList()

        for (round in archerData.rounds) {
            rounds.value?.add(RoundViewModel(round))
        }
    }

}