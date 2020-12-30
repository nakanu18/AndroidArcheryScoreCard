package com.deveradev.androidarcheryscorecard.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deveradev.androidarcheryscorecard.ui.AED_LOG_TAG
import com.deveradev.androidarcheryscorecard.ui.LogUtils
import kotlinx.coroutines.launch

class HistoryViewModel(context: Context) : ViewModel() {

    lateinit var archerData: ArcherData
    val rounds = MutableLiveData<ArrayList<RoundViewModel>>()
    var selectedRound = MutableLiveData<RoundViewModel>()

    init {
        this.viewModelScope.launch {
            archerData = ArcherDataRepository.getData(context)

            LogUtils.log("ArcherDataRepository: getData")
            for (round in archerData.rounds) {
                LogUtils.log("${ArcherData.getScorecardForRound(round)}")
            }

            rounds.value = ArrayList()
            for (round in archerData.rounds) {
                rounds.value?.add(RoundViewModel(round))
            }
        }
    }

}