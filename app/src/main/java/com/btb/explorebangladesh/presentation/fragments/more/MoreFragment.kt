package com.btb.explorebangladesh.presentation.fragments.more

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.StaticPage
import com.btb.explorebangladesh.databinding.FragmentMoreBinding
import com.btb.explorebangladesh.presentation.activities.auth.AuthActivity
import com.btb.explorebangladesh.presentation.activities.main.MainViewModel
import com.btb.explorebangladesh.presentation.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoreFragment : BaseFragment<MainViewModel, FragmentMoreBinding>(
    R.layout.fragment_more
) {
    override val viewModel by activityViewModels<MainViewModel>()

    override fun initializeViewBinding(view: View) = FragmentMoreBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateSignInOrOut()
        setupListeners()
    }

    private fun updateSignInOrOut() {
        val resourceId = if (viewModel.hasLoggedIn()) {
            R.string.sign_out
        } else {
            R.string.sign_in
        }
        binding.tvSignInOrOut.setText(resourceId)
    }

    private fun setupListeners() {
        binding.cvAboutUs.setOnClickListener {
            navigateToWebViewFragment(StaticPage.AboutUs)
        }
        binding.cvNewsLetter.setOnClickListener {
        }
        binding.cvAppsSettings.setOnClickListener {
            navigateToSettingsFragment()
        }
        binding.cvTermsAndConditions.setOnClickListener {
            navigateToWebViewFragment(StaticPage.TermsCondition)
        }
        binding.cvPrivacyPolicy.setOnClickListener {
            navigateToWebViewFragment(StaticPage.PrivacyPolicy)
        }

        binding.cvSignInOrOut.setOnClickListener {
            if (viewModel.hasLoggedIn()) {
                viewModel.doLogout()
                navigateToHomeFragment()
            } else {
                navigateToAuthActivity()
            }
        }

    }


    private fun navigateToHomeFragment() {
        findNavController().navigate(
            R.id.homeFragment,
            bundleOf(),
            NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, true)
                .build()
        )
    }


    private fun navigateToSettingsFragment() {
        findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToSettingsFragment())
    }


    private fun navigateToWebViewFragment(page: StaticPage) {
        findNavController().navigate(
            MoreFragmentDirections.actionMoreFragmentToWebViewFragment(page)
        )
    }

    private fun navigateToAuthActivity() {
        requireActivity().startActivity(
            Intent(requireActivity(), AuthActivity::class.java)
        )
    }

    override fun onResume() {
        super.onResume()
        this.activity?.title = getString(R.string.more)
    }



}