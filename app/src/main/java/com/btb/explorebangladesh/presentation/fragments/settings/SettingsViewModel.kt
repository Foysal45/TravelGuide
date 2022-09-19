package com.btb.explorebangladesh.presentation.fragments.settings

import com.btb.explorebangladesh.presentation.fragments.base.BaseViewModel
import com.btb.explorebangladesh.util.UserFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userFactory: UserFactory
) : BaseViewModel() {

    fun doLogout() {
        userFactory.clear()
    }
}