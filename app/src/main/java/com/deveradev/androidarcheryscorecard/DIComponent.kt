package com.deveradev.androidarcheryscorecard

import com.deveradev.androidarcheryscorecard.data.ArcherDataRepository
import com.deveradev.androidarcheryscorecard.data.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface DIComponent {

    fun getArcheryDataRepository(): ArcherDataRepository

}