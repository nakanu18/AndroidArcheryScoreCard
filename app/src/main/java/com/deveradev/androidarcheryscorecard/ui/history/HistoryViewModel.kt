package com.deveradev.androidarcheryscorecard.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.deveradev.androidarcheryscorecard.MainApplication
import com.deveradev.androidarcheryscorecard.data.ArcherData
import com.deveradev.androidarcheryscorecard.data.Round
import com.deveradev.androidarcheryscorecard.utils.Utils
import com.deveradev.androidarcheryscorecard.utils.mutation
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var archerData: ArcherData

    val rounds = MutableLiveData<ArrayList<Round>>()
    var selectedRound = MutableLiveData<Round>()
    var selectedEnd = MutableLiveData<Int>(0)
    var deletedRound: Pair<Round, Int>? = null
    var isSelectedRoundEdited = false

    init {
        Utils.log("HistoryViewModel: init")

        this.viewModelScope.launch {
            val mainApplication = (application as MainApplication)
            val archerDataRepository = mainApplication.daggerDIComponent.getArcheryDataRepository()

            archerData = archerDataRepository.getData(mainApplication)
            Utils.log("ArcherDataRepository: getData")

            for (round in archerData.rounds) {
                Utils.log("${ArcherData.getScorecardForRound(round)}")
            }

            rounds.value = archerData.rounds
        }
    }

    fun createNewRoundForEditing() {
        this.selectedRound.value =
            ArcherData.createNewRound(archerData.rounds, archerData.roundFormats)
        this.selectedEnd.value = 0
        this.isSelectedRoundEdited = false
    }

    fun copyRoundForEditing(round: Round) {
        this.selectedRound.value = Utils.deepCopy(round)
        this.selectedEnd.value = 0
        this.isSelectedRoundEdited = false
    }

    fun updateCurrentArrowForSelectedRound(arrowScore: Int) {
        var log = "HistoryViewModel: update arrow ignored"

        this.selectedRound.mutation {
            this.selectedRound.value?.let { round ->
                this.selectedEnd.value?.let { selectedEnd ->
                    if (arrowScore == -1) {
                        if (ArcherData.eraseLastArrowScore(round, selectedEnd)) {
                            isSelectedRoundEdited = true
                            log = "HistoryViewModel: erase arrow"
                        }
                    } else {
                        if (ArcherData.setLastArrowScore(round, selectedEnd, arrowScore)) {
                            isSelectedRoundEdited = true
                            log = "HistoryViewModel: update arrow -> $arrowScore"

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

    fun selectEndCapped(endID: Int) {
        this.selectedRound.value?.let {
            this.selectedEnd.value = minOf(endID, ArcherData.findLastEmptyEnd(it))
            Utils.log("HistoryViewModel: select end ${this.selectedEnd.value}")
        }
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

    fun deleteRound(roundID: Int) {
        this.rounds.mutation {
            it.value?.let { rounds ->
                this.deletedRound = rounds[roundID] to roundID
                rounds.removeAt(roundID)
                Utils.log("HistoryViewModel: delete round $roundID")
            }
        }
    }

    fun undoDeletedRound() {
        this.rounds.mutation {
            it.value?.let { rounds ->
                deletedRound?.let { deletedRound ->
                    rounds.add(deletedRound.second, deletedRound.first)
                    Utils.log("HistoryViewModel: undo delete round ${deletedRound.second}")
                }
                deletedRound = null
            }
        }
    }

}