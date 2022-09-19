package com.btb.explorebangladesh.presentation.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.Resource
import com.btb.explorebangladesh.databinding.FragmentHomeBinding
import com.btb.explorebangladesh.presentation.activities.main.MainViewModel
import com.btb.explorebangladesh.presentation.fragments.base.BaseFragment
import com.btb.explorebangladesh.view.hide
import com.btb.explorebangladesh.view.show
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<MainViewModel, FragmentHomeBinding>(
    R.layout.fragment_home
) {

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    override val viewModel by activityViewModels<MainViewModel>()

    override fun initializeViewBinding(view: View) = FragmentHomeBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        subscribeObservers()

    }

    private fun setupRecyclerView() {
        binding.rvCategories.adapter = categoryAdapter
        categoryAdapter.setOnItemClickListener { _, item ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSearchFragment(
                    item
                )
//                NavOptions.Builder()
//                    .setPopUpTo(R.id.homeFragment, true)
//                    .build()
            )
        }
    }

    private fun subscribeObservers() {

        viewModel.getCategories().observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Failure -> {
                    viewModel.updateLoading(false)
                    binding.rvCategories.hide()
                    binding.llEmptyView.show()
                }
                is Resource.Loading -> {
                    viewModel.updateLoading(true)
                }
                is Resource.Success -> {
                    viewModel.updateLoading(false)

                    if (resource.data.isEmpty()) {
                        binding.rvCategories.hide()
                        binding.llEmptyView.show()
                    } else {
                        categoryAdapter.differ.submitList(resource.data)
                        binding.rvCategories.show()
                        binding.llEmptyView.hide()
                    }
                }
            }
        }
    }

}