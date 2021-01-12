package com.deveradev.androidarcheryscorecard

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

// DONE: figure out top margin on top of the pages
//      removed android:paddingTop="?attr/actionBarSize" from activity_main.xml
// DONE: unsure why xml needs string concat for data binding
//      accessing non-string properties will cause a crash
// TODO: unsure why Scorecard.totalScore computed property is crashing
// DONE: fetch saved data via coroutine
// TODO: find out how to dynamically add arrow buttons to the RoundEditor
// DONE: figure out how to create a color from an R.color inside xml
//      https://stackoverflow.com/questions/61269493/how-to-set-app-backgroundtint-using-android-databinding-livedata
//      https://stackoverflow.com/questions/5271387/how-can-i-get-color-int-from-color-resource
// DONE_BETTER: use Paris to add colors to a style and then apply that style dynamically
// TODO: fix keyboard not hiding when focus changes from text input to bottom nav
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_history, R.id.navigation_tags))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

}