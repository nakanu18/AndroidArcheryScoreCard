package com.deveradev.androidarcheryscorecard

import android.app.Application

class MainApplication : Application() {

    val daggerDIComponent: DIComponent = DaggerDIComponent.create()

}