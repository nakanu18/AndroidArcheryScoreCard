package com.deveradev.androidarcheryscorecard.utils

import android.os.Build
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.airbnb.paris.extensions.style
import com.deveradev.androidarcheryscorecard.ui.AED_LOG_TAG
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Utils {

    fun log(description: String) {
        Log.i(AED_LOG_TAG, description)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFormattedDate(currentDate: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm a")
        return currentDate.format(formatter)
    }

    inline fun <reified T> deepCopy(obj: T): T {
        val json = Gson().toJson(obj)
        return Gson().fromJson(json, T::class.java)
    }

}

// https://stackoverflow.com/questions/48020377/livedata-update-on-object-field-change
//
// liveData.mutation {
//    it.value?.name = "Ed Khalturin"
//    it.value?.innerClass?.city= "Moscow" // it works with inner class too
//}
fun <T> MutableLiveData<T>.mutation(actions: (MutableLiveData<T>) -> Unit) {
    actions(this)
    this.value = this.value
}

// Bind style using Data Binding via Paris
//
// https://stackoverflow.com/questions/31980342/android-data-binding-style
// https://github.com/airbnb/paris
@BindingAdapter("bindStyle")
fun TextView.bindStyle(styleResourceId: Int) {
    this.style(styleResourceId)
}