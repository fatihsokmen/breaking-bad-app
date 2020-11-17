package com.github.fatihsokmen.breakingbad.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.github.fatihsokmen.breakingbad.R
import com.github.fatihsokmen.breakingbad.databinding.ActivityHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }
    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        NavigationUI.setupWithNavController(binding.toolbar, navController)
        observeNavigation()
    }

    private fun observeNavigation() {
        viewModel.navigator.onNavigateAppearanceFilter.observe(this) {
            navController.navigate(R.id.appearance_filter_fragment)
        }
        viewModel.navigator.onNavigateCharacterDetails.observe(this) {
            navController.navigate(R.id.fragment_character_details)
        }
    }
}