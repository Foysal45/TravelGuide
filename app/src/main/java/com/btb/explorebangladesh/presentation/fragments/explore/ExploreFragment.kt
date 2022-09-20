package com.btb.explorebangladesh.presentation.fragments.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.databinding.FragmentExploreBinding
import com.btb.explorebangladesh.presentation.fragments.base.BaseFragment
import com.btb.explorebangladesh.presentation.fragments.home.search.result.ArticleAdapter
import com.btb.explorebangladesh.view.hide
import com.btb.explorebangladesh.view.show
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment : BaseFragment<ExploreViewModel, FragmentExploreBinding>(
    R.layout.fragment_explore
) {

    @Inject
    lateinit var articleAdapter: ArticleAdapter

    override val viewModel by viewModels<ExploreViewModel>()

    override fun initializeViewBinding(view: View) = FragmentExploreBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchArticles()
        setupRecyclerViews()
        subscribeObservers()
    }

    private fun setupRecyclerViews() {
        binding.rvArticles.adapter = articleAdapter
        articleAdapter.setOnItemClickListener { _, item ->
            findNavController().navigate(
                ExploreFragmentDirections.actionExploreFragmentToDetailFragment(
                    item.id
                )
            )
        }
    }

    private fun subscribeObservers() {

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error ?: return@observe
            binding.rvArticles.hide()
            binding.llEmptyView.show()
            binding.tvMessage.text = error.asString(requireContext())
        }

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
    }
    override fun onResume() {
        super.onResume()
        this.activity?.title = getString(R.string.explore)
    }


}