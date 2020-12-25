package com.deveradev.androidarcheryscorecard.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel : ViewModel() {

    val records = MutableLiveData<ArrayList<RecordViewModel>>()

    companion object {

        fun createMockData(numRecords: Int): ArrayList<RecordViewModel> {
            val newRecords = ArrayList<RecordViewModel>()

            for (i in 0..numRecords) {
                newRecords.add(RecordViewModel(i, i, i * 2))
            }
            return newRecords
        }

    }

}