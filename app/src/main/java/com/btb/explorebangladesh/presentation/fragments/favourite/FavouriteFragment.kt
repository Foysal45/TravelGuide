package com.btb.explorebangladesh.presentation.fragments.favourite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.UiText
import com.btb.explorebangladesh.activity.showDialog
import com.btb.explorebangladesh.databinding.FragmentFavouriteBinding
import com.btb.explorebangladesh.presentation.activities.auth.AuthActivity
import com.btb.explorebangladesh.presentation.fragments.base.BaseFragment
import com.btb.explorebangladesh.presentation.fragments.home.search.result.ArticleAdapter
import com.btb.explorebangladesh.view.hide
import com.btb.explorebangladesh.view.show
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteFragment : BaseFragment<FavouriteViewModel, FragmentFavouriteBinding>(
    R.layout.fragment_favourite
) {
    @Inject
    lateinit var articleAdapter: ArticleAdapter


    override val viewModel by viewModels<FavouriteViewModel>()

    override fun initializeViewBinding(view: View) = FragmentFavouriteBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.hasLoggedIn()) {
            viewModel.fetchWishList()
        } else {
            navigateToAuthActivity()
        }
        setupRecyclerViews()
        subscribeObservers()
    }

    private fun setupRecyclerViews() {
        binding.rvArticles.adapter = articleAdapter
        articleAdapter.setOnItemClickListener { _, item ->
            findNavController().navigate(
                FavouriteFragmentDirections.actionFavouriteFragmentToDetailFragment(
                    item.id
                )
            )
        }
    }

    private fun subscribeObservers() {
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            if (articles.isNullOrEmpty()) {
                binding.rvArticles.hide()
                binding.llEmptyView.show()
            } else {
                articleAdapter.differ.submitList(articles)
                binding.rvArticles.show()
                binding.llEmptyView.hide()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error ?: return@observe
            binding.rvArticles.hide()
            binding.llEmptyView.show()
            binding.tvMessage.text = error.asString(requireContext())
        }
    }

    private fun showError(text: UiText) {
        requireContext().showDialog(
            title = getString(R.string.error),
            message = text.asString(requireContext())
        ) {

        }
    }

    private fun navigateToAuthActivity() {
        requireActivity().startActivity(
            Intent(requireActivity(), AuthActivity::class.java)
        )
    }

}