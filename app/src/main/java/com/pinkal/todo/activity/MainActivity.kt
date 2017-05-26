package com.pinkal.todo.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import com.pinkal.todo.R
import com.pinkal.todo.fragment.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val TAG: String = MainActivity::class.java.simpleName

    val mActivity: Activity = this@MainActivity

    var toolbar: Toolbar? = null
    var drawer: DrawerLayout? = null
    var navigationView: NavigationView? = null
    var framLayout: FrameLayout? = null
    var handler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.framLayout, DashboardFragment())
        ft.commit()
    }

    fun initialize() {
        toolbar = findViewById(R.id.toolbarMain) as Toolbar
        drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        navigationView = findViewById(R.id.nav_view) as NavigationView
        framLayout = findViewById(R.id.framLayout) as FrameLayout

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer!!.setDrawerListener(toggle)
        toggle.syncState()

        handler = Handler()

        navigationView!!.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        when (id) {
            R.id.action_settings -> {
                startActivity(Intent(mActivity, SettingActivity::class.java))
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        handler!!.postDelayed({ navigate(item.itemId) }, 300)
        return true
    }

    fun navigate(id: Int) {
        var fragment: Fragment? = null
        var fragmentClass: Class<*>? = null

        when (id) {
            R.id.nav_dashboard -> {
                fragmentClass = DashboardFragment::class.java
                toolbar!!.title = "Dashboard"
            }
            R.id.nav_category -> {
                fragmentClass = ManageCategoryFragment::class.java
                toolbar!!.title = "Category"
            }
            R.id.nav_history -> {
                fragmentClass = HistoryFragment::class.java
                toolbar!!.title = "History"
            }
            R.id.nav_rate_us -> {
                fragmentClass = RateUsFragment::class.java
                toolbar!!.title = "Rate Us"
            }
            R.id.nav_share_app -> {
                fragmentClass = ShareAppFragment::class.java
                toolbar!!.title = "Share App"
            }
        }

        drawer!!.closeDrawer(GravityCompat.START)

        try {
            fragment = fragmentClass!!.newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }


        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.framLayout, fragment).commit()
    }
}