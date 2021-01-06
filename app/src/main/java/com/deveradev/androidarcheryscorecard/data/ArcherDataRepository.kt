package com.deveradev.androidarcheryscorecard.data

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArcherDataRepository @Inject constructor(private val archerDataService: ArcherDataService) {

    private var archerData: ArcherData? = null

    suspend fun getData(context: Context): ArcherData {
        if (this.archerData == null) {
            this.archerData = this.archerDataService.getData(context)

            if (this.archerData == null) {
                this.archerData = getNewData()
            }
        }
        return this.archerData!!
    }

    private fun getNewData(): ArcherData {
        return ArcherData(
            arrayListOf<RoundFormat>(
                RoundFormat(0, 30, "20yd", "Vegas 300", 3, 300)
            ),
            arrayListOf<Round>(),
            "",
            1,
            arrayListOf<Tag>(
                Tag(0, "SF Premium Riser"),
                Tag(1, "SF Axiom Limbs 32#"),
                Tag(3, "Easton Tribute Arrows 1916")
            )
        )
    }

}