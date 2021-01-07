package com.deveradev.androidarcheryscorecard.utils

import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.airbnb.paris.extensions.style
import com.deveradev.androidarcheryscorecard.ui.AED_LOG_TAG
import com.deveradev.androidarcheryscorecard.ui.UNKNOWN_VALUE
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun log(description: String) {
        Log.i(AED_LOG_TAG, description)
    }

    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(currentDate: Date): String {
        val formatter = SimpleDateFormat("EEE, MMM d, yyyy h:mm a", Locale.US)
        return formatter.format(currentDate)
    }

    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(currentDate: String): String {
        val formatter = SimpleDateFormat("MM-dd-yyyy h:mm a", Locale.US)
        formatter.parse(currentDate)?.let {
           return getFormattedDate(it)
        }
        return UNKNOWN_VALUE
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