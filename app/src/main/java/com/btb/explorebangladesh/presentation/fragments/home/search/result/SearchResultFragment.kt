package com.btb.explorebangladesh.presentation.fragments.home.search.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.btb.explorebangladesh.PaginationListener
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.databinding.FragmentSearchResultBinding
import com.btb.explorebangladesh.domain.model.Article
import com.btb.explorebangladesh.presentation.fragments.base.BaseFragment
import com.btb.explorebangladesh.presentation.fragments.home.search.SearchViewModel
import com.btb.explorebangladesh.view.hide
import com.btb.explorebangladesh.view.show
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SearchResultFragment : BaseFragment<SearchViewModel, FragmentSearchResultBinding>(
    R.layout.fragment_search_result
) {
    @Inject
    lateinit var articleAdapter: ArticleAdapter

    private var currentPage = PaginationListener.PAGE_START
    private var isLastPage = false
    private var isLoading = false

    private val mArticles = mutableListOf<Article>()


    override val viewModel by activityViewModels<SearchViewModel>()

    override fun initializeViewBinding(view: View) = FragmentSearchResultBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        subscribeObservers()
    }

    override fun onResume() {
        super.onResume()

        viewModel.searchArticles(currentPage)
    }

    private fun setupRecyclerViews() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvArticles.adapter = articleAdapter
        binding.rvArticles.layoutManager = layoutManager

        binding.rvArticles.addOnScrollListener(object : PaginationListener(layoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage++
                viewModel.searchArticles(currentPage)
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })

        articleAdapter.setOnItemClickListener { _, item ->
            findNavController().navigate(
                SearchResultFragmentDirections.actionSearchResultFragmentToDetailFragment(
                    item.id
                )
//                NavOptions.Builder()
//                    .setPopUpTo(R.id.homeFragment, true)
//                    .build()
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
                isLastPage = true
                binding.rvArticles.hide()
                binding.llEmptyView.show()
            } else {
                if(currentPage == PaginationListener.PAGE_START){
                    mArticles.clear()
                }
                mArticles.addAll(articles)
                articleAdapter.differ.submitList(mArticles)
                binding.rvArticles.show()
                binding.llEmptyView.hide()
            }
            isLoading = false
        }
    }
}