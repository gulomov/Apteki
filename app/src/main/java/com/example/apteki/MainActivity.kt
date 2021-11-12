package com.example.apteki

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.example.apteki.data.isLoggedIn
import com.example.apteki.databinding.ActivityMainBinding
import com.example.apteki.utils.NavigationUiHelper

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout
        navView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_dashboard, R.id.nav_branches, R.id.nav_products
            ), drawerLayout
        )

        navView.setupWithNavController(navController)
        setSupportActionBar(binding.contentMenu.appBarMain.toolbarMain)
        supportActionBar?.setDisplayShowTitleEnabled(false);
        setupActionBar(navController)
        setUpDrawer()
        goToLogIn()
    }

    private fun goToLogIn() {
        if (!this.isLoggedIn()) {
            navController.popBackStack(
                R.id.nav_branches,
                true
            )
            navController.navigate(R.id.nav_logIn)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    private fun setupActionBar(navController: NavController) {
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
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
            binding.contentMenu.appBarMain.toolbarMain.visibility = View.VISIBLE
            binding.contentMenu.toolbarLogo.visibility = View.VISIBLE

            when (destination.id) {
                R.id.nav_branches -> {
                    binding.contentMenu.appBarMain.toolbarMain.navigationIcon =
                        ResourcesCompat.getDrawable(resources, R.drawable.nav_header_menu, null)
                }
                R.id.nav_dashboard -> {
                    binding.contentMenu.appBarMain.toolbarMain.navigationIcon =
                        ResourcesCompat.getDrawable(resources, R.drawable.ic_back, null)
                }
                R.id.nav_products -> {
                    binding.contentMenu.appBarMain.toolbarMain.navigationIcon =
                        ResourcesCompat.getDrawable(resources, R.drawable.ic_back, null)
                }
                R.id.nav_newBranches -> {
                    binding.contentMenu.appBarMain.toolbarMain.navigationIcon =
                        ResourcesCompat.getDrawable(resources, R.drawable.ic_back, null)
                }
                R.id.nav_trade_stats -> {
                    binding.contentMenu.appBarMain.toolbarMain.navigationIcon =
                        ResourcesCompat.getDrawable(resources, R.drawable.ic_back, null)
                }
                R.id.nav_logIn -> {
                    binding.contentMenu.appBarMain.toolbarMain.visibility = View.INVISIBLE
                    binding.contentMenu.toolbarLogo.visibility = View.INVISIBLE
                }
            }
        }
    }
}