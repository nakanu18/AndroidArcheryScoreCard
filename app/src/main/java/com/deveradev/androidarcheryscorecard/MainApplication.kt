package com.deveradev.androidarcheryscorecard

import android.app.Application
import com.deveradev.androidarcheryscorecard.data.DataModule

class MainApplication : Application() {

    // Use this for simple Dagger injection
//    val daggerDIComponent: DIComponent = DaggerDIComponent.create()

    // Use this for Dagger inject using Modules
    val daggerDIComponent: DIComponent = DaggerDIComponent
        .builder()
        .dataModule(DataModule())
        .build()

}