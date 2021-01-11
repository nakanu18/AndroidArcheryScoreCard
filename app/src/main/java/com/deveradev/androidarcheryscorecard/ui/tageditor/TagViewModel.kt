package com.deveradev.androidarcheryscorecard.ui.tageditor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.deveradev.androidarcheryscorecard.data.Tag

class TagViewModel(private val tag: Tag, application: Application): AndroidViewModel(application) {

    val name = this.tag.name
    val notes = this.tag.notes

}