package com.binar.secondhand

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.databinding.ActivityMainBinding
import com.binar.secondhand.ui.productlist.ProductListViewModel
import com.binar.secondhand.ui.profile.CompleteAccountActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        startActivity(Intent(this, CompleteAccountActivity::class.java))
        setupNav()
    }

    private fun setupNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostActivity) as NavHostFragment
        val navController = navHostFragment.navController



        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> { showBottomNav() }
                R.id.navigation_akun -> { showBottomNav() }
                R.id.navigation_jual -> { showBottomNav() }
                R.id.navigation_list -> { showBottomNav() }
                R.id.navigation_notif -> { showBottomNav() }
                else -> {
                    hideBottomNav()
                }
            }
        }

        binding.navView.setupWithNavController(navController)
    }

    private fun showBottomNav() {
        binding.navView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.navView.visibility = View.GONE
    }

}