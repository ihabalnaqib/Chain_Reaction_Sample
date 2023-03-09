package com.chainreaction.sample.view

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.cainreaction.sample.R
import com.cainreaction.sample.databinding.ActivityMainBinding
import com.chainreaction.sample.view.interfaces.OnProgressLoadingListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnProgressLoadingListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHost.findNavController()

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun showProgress() {
        if (!binding.waitingProgress.isVisible)
            binding.waitingProgress.isVisible = true
    }

    override fun hideProgress() {
        if (binding.waitingProgress.isVisible)
            binding.waitingProgress.isVisible = false
    }

    override fun showNoDataFound() {
        if (!binding.noDataTx.isVisible)
            binding.noDataTx.isVisible = true

    }

    override fun hideNoDataFound() {
        if (binding.noDataTx.isVisible)
            binding.noDataTx.isVisible = false
    }
}