package com.deveradev.androidarcheryscorecard.data

import android.content.Context
import android.util.Log
import com.deveradev.androidarcheryscorecard.R
import com.deveradev.androidarcheryscorecard.ui.AED_LOG_TAG
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArcherDataRepository {

    companion object {
        private var archerData: ArcherData? = null

        suspend fun getData(context: Context): ArcherData {
            archerData = if (archerData == null) {
                withContext(Dispatchers.IO) {
                    val file = context.resources
                        .openRawResource(R.raw.mock_saved_data)
                        .bufferedReader()
                        .readText()
                    return@withContext Gson().fromJson(file, ArcherData::class.java)
                }
            } else {
                getNewData()
            }
            return archerData!!
        }

        private fun getNewData(): ArcherData {
            return ArcherData(
                listOf<RoundFormat>(
                    RoundFormat(0, 30, "20yd", "Vegas 300", 3, 300)
                ),
                listOf<Round>(),
                "",
                1,
                listOf<Tag>(
                    Tag(0, "SF Premium Riser"),
                    Tag(1, "SF Axiom Limbs 32#"),
                    Tag(3, "Easton Tribute Arrows 1916")
                )
            )
        }

    }

}