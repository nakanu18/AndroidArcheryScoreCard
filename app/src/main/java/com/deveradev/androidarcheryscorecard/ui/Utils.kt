package com.deveradev.androidarcheryscorecard.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.deveradev.androidarcheryscorecard.data.Round
import com.google.gson.Gson

object Utils {

    fun log(description: String) {
        Log.i(AED_LOG_TAG, description)
    }

    inline fun <reified  T> deepCopy(obj: T): T {
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

