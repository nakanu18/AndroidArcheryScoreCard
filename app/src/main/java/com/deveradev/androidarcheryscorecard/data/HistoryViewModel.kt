package com.deveradev.androidarcheryscorecard.data

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.deveradev.androidarcheryscorecard.ui.Utils
import com.deveradev.androidarcheryscorecard.ui.mutation
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var archerData: ArcherData

    val rounds = MutableLiveData<ArrayList<Round>>()
    var selectedRound = MutableLiveData<Round>()
    var selectedEnd = MutableLiveData<Int>(0)
    var isSelectedRoundEdited = false

    init {
        Utils.log("HistoryViewModel: init")

        this.viewModelScope.launch {
            archerData = ArcherDataRepository.getData(getApplication())
            Utils.log("ArcherDataRepository: getData")

            for (round in archerData.rounds) {
                Utils.log("${ArcherData.getScorecardForRound(round)}")
            }

            rounds.value = archerData.rounds
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNewRoundForEditing() {
        this.selectedRound.value = ArcherData.createNewRound(archerData.rounds, archerData.roundFormats)
        this.selectedEnd.value = 0
        this.isSelectedRoundEdited = false
    }

    fun copyRoundForEditing(round: Round) {
        this.selectedRound.value = Utils.deepCopy(round)
        this.selectedEnd.value = 0
        this.isSelectedRoundEdited = false
    }

    fun updateCurrentArrowForSelectedRound(arrowScore: Int) {
        var log = "RoundEditorFragment: update arrow ignored"

        this.selectedRound.mutation {
            this.selectedRound.value?.let { round ->
                this.selectedEnd.value?.let { selectedEnd ->
                    if (arrowScore == -1) {
                        if (ArcherData.eraseLastArrowScore(round, selectedEnd)) {
                            isSelectedRoundEdited = true
                            log = "RoundEditorFragment: erase arrow"
                        }
                    } else {
                        if (ArcherData.setLastArrowScore(round, selectedEnd, arrowScore)){
                            isSelectedRoundEdited = true
                            log = "RoundEditorFragment: update arrow -> $arrowScore"

                            if (ArcherData.findFirstUnassignedArrowID(round, selectedEnd) == -1) {
                                selectNextEnd()
                            }
                        }
                    }
                }
            }
        }
        Utils.log(log)
    }

    fun selectNextEnd() {
        val numEnds = this.selectedRound.value?.roundFormat?.numEnds ?: 1

        this.selectedEnd.value?.let {
            selectedEnd.value = minOf(it + 1, numEnds - 1)
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
                    } else {
                        rounds.add(0, selectedRound)
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