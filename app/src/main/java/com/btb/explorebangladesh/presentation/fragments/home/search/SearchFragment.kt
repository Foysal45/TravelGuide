package com.btb.explorebangladesh.presentation.fragments.home.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.databinding.FragmentSearchBinding
import com.btb.explorebangladesh.domain.model.District
import com.btb.explorebangladesh.presentation.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>(
    R.layout.fragment_search
) {

    private val args by navArgs<SearchFragmentArgs>()

    private val category by lazy {
        args.category
    }

    private var districtAdapter: DistrictAdapter? = null

    override val viewModel by activityViewModels<SearchViewModel>()

    override fun initializeViewBinding(view: View) = FragmentSearchBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinner()
        setupListeners()
        subscribeObservers()
        updateTitle()
        viewModel.onSearchEvent(SearchEvent.CategoryIdChanged(category.id))

    }

    private fun updateTitle() {
        binding.tvTitle.text = category.title
    }

    private fun setupListeners() {

        binding.etSearch.addTextChangedListener {
            it?.toString()?.let { text ->
                viewModel.onSearchEvent(SearchEvent.TitleChanged(text))
            }
        }

        binding.btnSearch.setOnClickListener {
//            viewModel.onSearchEvent(SearchEvent.Search)
            navigateToSearchResultFragment()
        }
    }

    private fun setupSpinner() {
        districtAdapter = DistrictAdapter(binding.psvDistrict)
        districtAdapter?.let {
            binding.psvDistrict.setSpinnerAdapter(it)
        }
        binding.psvDistrict.setOnSpinnerItemSelectedListener<District> { _, _, _, _ ->
            Log.e("SearchFragment", "setupSpinner: ")
        }
        binding.psvDistrict.lifecycleOwner = viewLifecycleOwner
    }

    private fun subscribeObservers() {
        viewModel.districts.observe(viewLifecycleOwner) { districts ->
            districtAdapter?.setItems(districts.sortedBy { it.name })
        }

    }

    private fun navigateToSearchResultFragment() {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToSearchResultFragment()
//            NavOptions.Builder()
//                .setPopUpTo(R.id.homeFragment, true)
//                .build()
        )
    }

}