package com.deveradev.androidarcheryscorecard.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deveradev.androidarcheryscorecard.ui.Utils
import com.deveradev.androidarcheryscorecard.ui.mutation
import kotlinx.coroutines.launch

// TODO: change to use AndroidViewModel so we don't have to pass in context
class HistoryViewModel(context: Context) : ViewModel() {

    lateinit var archerData: ArcherData

    val rounds = MutableLiveData<ArrayList<Round>>()
    var selectedRound = MutableLiveData<Round>()
    var selectedEnd = MutableLiveData<Int>(0)

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

    fun saveSelectedRound() {
        this.rounds.mutation {
            // Save selectedRound to correct spot in rounds
            this.selectedRound.value?.let { selectedRound ->
                this.rounds.value?.let { rounds ->
                    val index = rounds.indexOfFirst { round -> round.ID == selectedRound.ID }
                    if (index != -1) {
                        rounds[index] = selectedRound
                    }
                }
            }
        }
        this.selectedRound.value = null
    }

    fun discardSelectedRound() {
        this.selectedRound.value = null
    }

}