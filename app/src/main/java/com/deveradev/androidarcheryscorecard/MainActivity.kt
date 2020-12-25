package com.deveradev.androidarcheryscorecard

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.deveradev.androidarcheryscorecard.data.ArcherDataRepository
import com.deveradev.androidarcheryscorecard.ui.AED_LOG_TAG

// DONE: figure out top margin on top of the pages
//      removed android:paddingTop="?attr/actionBarSize" from activity_main.xml
// TODO: unsure why xml needs string concat for data binding
// TODO: fetch saved data via coroutine
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_bows))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val data = ArcherDataRepository.getData(this)

        Log.i(AED_LOG_TAG, "Total Score: ${data.rounds[0].getTotalScore()}")
    }

}