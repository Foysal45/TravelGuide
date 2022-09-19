package com.btb.explorebangladesh.presentation.fragments.web

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.btb.explorebangladesh.Resource
import com.btb.explorebangladesh.StaticPage
import com.btb.explorebangladesh.domain.repository.BtbRepository
import com.btb.explorebangladesh.lang.LanguageProvider
import com.btb.explorebangladesh.presentation.fragments.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val repository: BtbRepository,
    private val languageProvider: LanguageProvider
) : BaseViewModel() {

    private val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description

    fun getStaticDetail(page: StaticPage) {
        val language = languageProvider.getCurrentLanguage()
        viewModelScope.launch {
            when (val resource = repository.getStaticDetail(page, language)) {
                is Resource.Failure -> {
                    updateLoading(false)
                    _error.postValue(resource.text)

                }
                is Resource.Loading -> {
                    updateLoading(true)
                }
                is Resource.Success -> {
                    updateLoading(false)
                    _description.postValue(resource.data)
                }
            }
        }
    }

}