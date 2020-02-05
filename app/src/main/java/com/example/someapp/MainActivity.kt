package com.example.someapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar



class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        pref = this@MainActivity.getPreferences(Context.MODE_PRIVATE)
        if (pref.getBoolean("NIGHT_MODE", false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val snackBar = Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
            snackBar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
            snackBar.show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        if (pref.getBoolean("NIGHT_MODE", false)){
            navView.background = ContextCompat.getDrawable(this, R.drawable.nav_back_night)
        }

        val navController = findNavController(R.id.nav_host_fragment)
        if (pref.getBoolean("NIGHT_MODE_CHANGED", false)){
            navController.navigate(R.id.nav_settings)
            pref.edit().putBoolean("NIGHT_MODE_CHANGED", false).apply()
        }
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_disappear,
                R.id.nav_home, R.id.nav_settings,
                R.id.nav_share
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }


}
