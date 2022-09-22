package com.btb.explorebangladesh.presentation.fragments.web

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.StaticPage
import com.btb.explorebangladesh.databinding.FragmentWebViewBinding
import com.btb.explorebangladesh.presentation.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : BaseFragment<WebViewViewModel, FragmentWebViewBinding>(
    R.layout.fragment_web_view
) {
    private val args by navArgs<WebViewFragmentArgs>()

    private val page by lazy {
        args.page
    }

    override val viewModel by viewModels<WebViewViewModel>()

    override fun initializeViewBinding(view: View) = FragmentWebViewBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateTitle()
        subscribeObservers()
    }

    private fun updateTitle() {
        val titleRes = when (page) {
            StaticPage.AboutUs -> R.string.about_us
            StaticPage.PrivacyPolicy -> R.string.privacy_policy
            StaticPage.TermsCondition -> R.string.terms
            StaticPage.Unknown -> R.string.unknown
        }
        binding.tvTitle.setText(titleRes)
    }

    private fun subscribeObservers() {
        viewModel.getStaticDetail(page)
        viewModel.description.observe(viewLifecycleOwner) { description ->
            description ?: return@observe
            binding.tvDescription.text = description
        }
    }

    override fun onResume() {
        super.onResume()
        this.activity?.title = ""

    }
}