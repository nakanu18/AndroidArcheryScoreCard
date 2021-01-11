package com.deveradev.androidarcheryscorecard.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
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
    fun getDisplayDate(currentDate: String): String {
        val formatter = SimpleDateFormat("MM-dd-yyyy h:mm a", Locale.US)
        formatter.parse(currentDate)?.let {
            val displayFormatter = SimpleDateFormat("EEE, MMM d, yyyy h:mm a", Locale.US)
            return displayFormatter.format(it)
        }
        return UNKNOWN_VALUE
    }

    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(currentDate: Date): String {
        val formatter = SimpleDateFormat("MM-dd-yyyy h:mm a", Locale.US)
        return formatter.format(currentDate)
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

// Hide soft keyboard
//
// https://stackoverflow.com/questions/1109022/how-do-you-close-hide-the-android-soft-keyboard-using-java
fun Fragment.hideKeyboard() {
    val view = requireActivity().currentFocus
    view?.let {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

// Bind style using Data Binding via Paris
//
// https://stackoverflow.com/questions/31980342/android-data-binding-style
// https://github.com/airbnb/paris
@BindingAdapter("bindStyle")
fun TextView.bindStyle(styleResourceId: Int) {
    this.style(styleResourceId)
}