package com.example.apteki

import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
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


        setUpDrawer()
        goToLogIn()
        drawerClick()
        onDestinationChanged()
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
        navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun setUpDrawer() {
        drawerLayout = binding.drawerLayout
        navView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_dashboard, R.id.nav_branches, R.id.nav_products
            ), drawerLayout
        )

        navView.setupWithNavController(navController)


        navView.setNavigationItemSelectedListener { item ->
            val builder = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.enter)
                .setExitAnim(R.anim.exit)
                .setPopEnterAnim(R.anim.pop_enter)
                .setPopExitAnim(R.anim.pop_exit)
            NavigationUiHelper.onNavDestinationSelected(item, navController, builder)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


    }

    private fun onDestinationChanged() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->


            when (destination.id) {
                R.id.nav_branches -> {
                    binding.appBarMain.toolbarLogo.visibility = View.VISIBLE
                    binding.appBarMain.whiteLine.visibility = View.VISIBLE
                    binding.appBarMain.backIcon.visibility = View.GONE
                    binding.appBarMain.drawerIcon.visibility = View.VISIBLE
                }
                R.id.nav_branches_info -> {
                    binding.appBarMain.toolbarLogo.visibility = View.VISIBLE
                    binding.appBarMain.whiteLine.visibility = View.VISIBLE
                    binding.appBarMain.backIcon.visibility = View.VISIBLE
                    binding.appBarMain.drawerIcon.visibility = View.GONE
                }
                R.id.nav_products -> {
                    binding.appBarMain.toolbarLogo.visibility = View.VISIBLE
                    binding.appBarMain.whiteLine.visibility = View.VISIBLE
                    binding.appBarMain.backIcon.visibility = View.GONE
                    binding.appBarMain.drawerIcon.visibility = View.VISIBLE
                }
                R.id.nav_newBranches -> {
                    binding.appBarMain.toolbarLogo.visibility = View.VISIBLE
                    binding.appBarMain.whiteLine.visibility = View.VISIBLE
                    binding.appBarMain.backIcon.visibility = View.VISIBLE
                    binding.appBarMain.drawerIcon.visibility = View.GONE
                }
                R.id.nav_trade_stats -> {
                    binding.appBarMain.toolbarLogo.visibility = View.VISIBLE
                    binding.appBarMain.whiteLine.visibility = View.VISIBLE
                    binding.appBarMain.backIcon.visibility = View.VISIBLE
                    binding.appBarMain.drawerIcon.visibility = View.GONE
                }
                R.id.nav_invoice_stats -> {
                    binding.appBarMain.toolbarLogo.visibility = View.VISIBLE
                    binding.appBarMain.whiteLine.visibility = View.VISIBLE
                    binding.appBarMain.backIcon.visibility = View.VISIBLE
                    binding.appBarMain.drawerIcon.visibility = View.GONE
                }
                R.id.nav_employee_fragment -> {
                    binding.appBarMain.toolbarLogo.visibility = View.VISIBLE
                    binding.appBarMain.whiteLine.visibility = View.VISIBLE
                    binding.appBarMain.backIcon.visibility = View.VISIBLE
                    binding.appBarMain.drawerIcon.visibility = View.GONE
                }
                R.id.nav_employee_info -> {
                    binding.appBarMain.toolbarLogo.visibility = View.VISIBLE
                    binding.appBarMain.whiteLine.visibility = View.VISIBLE
                    binding.appBarMain.backIcon.visibility = View.VISIBLE
                    binding.appBarMain.drawerIcon.visibility = View.GONE
                }
                R.id.nav_add_employee -> {
                    binding.appBarMain.toolbarLogo.visibility = View.VISIBLE
                    binding.appBarMain.whiteLine.visibility = View.VISIBLE
                    binding.appBarMain.backIcon.visibility = View.VISIBLE
                    binding.appBarMain.drawerIcon.visibility = View.GONE
                }
                R.id.nav_logIn -> {
                    binding.appBarMain.backIcon.visibility = View.GONE
                    binding.appBarMain.drawerIcon.visibility = View.GONE
                    binding.appBarMain.toolbarLogo.visibility = View.GONE
                    binding.appBarMain.whiteLine.visibility = View.GONE
                }
            }
        }
    }

    private fun drawerClick() {
        binding.appBarMain.drawerIcon.setOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.LEFT)
        }
        binding.appBarMain.backIcon.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers()
            } else {

                super.onBackPressed()
            }
        }
    }

}