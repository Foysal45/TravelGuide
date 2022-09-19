package com.btb.explorebangladesh.presentation.activities.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.btb.explorebangladesh.util.UserFactory
import com.btb.explorebangladesh.presentation.fragments.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val userFactory: UserFactory
) : BaseViewModel() {

    private val _token = MutableLiveData(userFactory.getAccessToken())

    val hasLoggedIn: LiveData<Boolean> = Transformations.map(_token) { !it.isNullOrEmpty() }

}