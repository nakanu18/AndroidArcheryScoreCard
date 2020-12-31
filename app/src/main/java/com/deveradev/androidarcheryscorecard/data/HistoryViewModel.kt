package com.deveradev.androidarcheryscorecard.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deveradev.androidarcheryscorecard.ui.Utils
import kotlinx.coroutines.launch

class HistoryViewModel(context: Context) : ViewModel() {

    lateinit var archerData: ArcherData

    val rounds = MutableLiveData<ArrayList<Round>>()
    var selectedRound = MutableLiveData<Round>()

    init {
        Utils.log("HistoryViewModel: init")

        this.viewModelScope.launch {
            archerData = ArcherDataRepository.getData(context)
            Utils.log("ArcherDataRepository: getData")

            for (round in archerData.rounds) {
                Utils.log("${ArcherData.getScorecardForRound(round)}")
            }

            rounds.value = archerData.rounds
        }
    }

}