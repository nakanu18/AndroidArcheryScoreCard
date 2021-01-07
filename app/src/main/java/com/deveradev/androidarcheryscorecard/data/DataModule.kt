package com.deveradev.androidarcheryscorecard.data

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun getGson() = Gson()

}