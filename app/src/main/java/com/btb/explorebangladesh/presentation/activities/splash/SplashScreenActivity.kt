package com.btb.explorebangladesh.presentation.activities.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.btb.explorebangladesh.databinding.ActivitySplashScreenBinding
import com.btb.explorebangladesh.presentation.activities.auth.AuthActivity
import com.btb.explorebangladesh.presentation.activities.base.BaseActivity
import com.btb.explorebangladesh.presentation.activities.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    private val viewModel by viewModels<SplashScreenViewModel>()

    override fun initializeViewBinding() = ActivitySplashScreenBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(mainLooper).postDelayed({
            handleNavigation()
        }, 2000)
    }

    private fun handleNavigation() {
//        viewModel.hasLoggedIn.observe(this) { hasLoggedIn ->
//            if (hasLoggedIn) {
//                navigateToMainActivity()
//            } else {
//                navigateToAuthActivity()
//            }
//        }

        navigateToMainActivity()

    }

    private fun navigateToAuthActivity() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


}