package com.deveradev.androidarcheryscorecard.ui

import android.util.Log
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