package com.btb.explorebangladesh.presentation.activities.main


import com.btb.explorebangladesh.domain.repository.BtbRepository
import com.btb.explorebangladesh.presentation.fragments.base.BaseViewModel
import com.btb.explorebangladesh.util.UserFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BtbRepository,
    private val userFactory: UserFactory
) : BaseViewModel() {


    fun hasLoggedIn(): Boolean = userFactory.getAccessToken().isNotEmpty()

    fun doLogout() {
        userFactory.clear()
    }

    fun getCategories() = repository.getCategories(
        mapOf(
            "page" to 1, "limit" to 10000
        )
    )




}