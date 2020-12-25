package com.deveradev.androidarcheryscorecard.data

import androidx.lifecycle.ViewModel

class RecordViewModel(
    val recordID: Int,
    val roundID: Int,
    val score: Int) : ViewModel() {
}