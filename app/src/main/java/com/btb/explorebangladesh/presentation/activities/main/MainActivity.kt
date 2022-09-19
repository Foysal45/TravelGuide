package com.btb.explorebangladesh.presentation.activities.main

import android.os.Bundle
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.StaticPage
import com.btb.explorebangladesh.activity.findNavController
import com.btb.explorebangladesh.databinding.ActivityMainBinding
import com.btb.explorebangladesh.presentation.activities.base.BaseActivity
import com.btb.explorebangladesh.view.hide
import com.btb.explorebangladesh.view.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val navController by lazy {
        findNavController(binding.navHostMainFragment.id)
    }

    override fun initializeViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setSupportActionBar(binding.toolbar)
        setupNavigation()

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }


    private fun setupNavigation() {
        binding.bottomNav.setupWithNavController(navController)
//        NavigationUI.setupWithNavController(binding.toolbar, navController)

        navController.addOnDestinationChangedListener { _, destination, arguments ->
            val title = if (destination.id == R.id.webViewFragment) {
                val staticPage = arguments?.getSerializable("page") as StaticPage?
                if (staticPage != null) {
                    when (staticPage) {
                        StaticPage.AboutUs -> getString(R.string.about_us)
                        StaticPage.PrivacyPolicy -> getString(R.string.privacy_policy)
                        StaticPage.TermsCondition -> getString(R.string.terms)
                        StaticPage.Unknown -> getString(R.string.unknown)
                    }
                } else {
                    destination.label?.toString()
                }
            } else {
                destination.label?.toString()
            }

            if(destination.id in listOf(R.id.homeFragment, R.id.exploreFragment, R.id.favouriteFragment, R.id.moreFragment)){
                binding.bottomNav.show()
            } else {
                binding.bottomNav.hide()
            }
        }
    }


}