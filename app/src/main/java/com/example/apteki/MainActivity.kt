package com.example.apteki

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.ui.*
import com.example.apteki.databinding.ActivityMainBinding
import com.example.apteki.utils.NavigationUiHelper

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var navView: NavigationView
    private lateinit var drawerIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.contentMenu.appBarMain.toolbarMain)


        drawerLayout = binding.drawerLayout
        navView = binding.navView
        drawerIcon = binding.contentMenu.appBarMain.drawerIcon
        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_dashboard,R.id.nav_branches, R.id.nav_products
            ), drawerLayout
        )

        navView.setupWithNavController(navController)
        openOrCloseDrawer()
        setUpDrawer()
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun openOrCloseDrawer() {
        binding.contentMenu.appBarMain.drawerIcon.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }
        binding.contentMenu.contentId.setOnClickListener {
            drawerLayout.closeDrawer(Gravity.LEFT)
        }
    }

    private fun setUpDrawer() {
        navView.setNavigationItemSelectedListener { item ->
            val builder = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.nav_default_enter_anim)
                .setExitAnim(R.anim.nav_default_exit_anim)
                .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
            NavigationUiHelper.onNavDestinationSelected(item, navController, builder)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.nav_branches -> {
                    drawerIcon.setImageResource(R.drawable.nav_header_menu)
                }
                R.id.nav_dashboard -> {
                    drawerIcon.setImageResource(R.drawable.ic_back)
                }
                R.id.nav_products -> {
                    drawerIcon.setImageResource(R.drawable.ic_back)
                }
            }
        }

    }
}