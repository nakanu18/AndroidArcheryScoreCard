package com.deveradev.androidarcheryscorecard.data

import android.content.Context
import com.deveradev.androidarcheryscorecard.R
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArcherDataService @Inject constructor() {

    @Inject lateinit var gson: Gson

    suspend fun getData(context: Context): ArcherData? {
        return withContext(Dispatchers.IO) {
            val file = context.resources
                .openRawResource(R.raw.mock_saved_data)
                .bufferedReader()
                .readText()
            return@withContext gson.fromJson(file, ArcherData::class.java)
        }
    }

}