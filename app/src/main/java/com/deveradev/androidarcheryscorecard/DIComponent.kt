package com.deveradev.androidarcheryscorecard

import com.deveradev.androidarcheryscorecard.data.ArcherDataRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface DIComponent {

    fun getArcheryDataRepository(): ArcherDataRepository

}