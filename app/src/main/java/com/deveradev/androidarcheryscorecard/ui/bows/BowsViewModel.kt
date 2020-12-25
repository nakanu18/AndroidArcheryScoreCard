package com.deveradev.androidarcheryscorecard.ui.bows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BowsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is bows Fragment"
    }
    val text: LiveData<String> = _text
}