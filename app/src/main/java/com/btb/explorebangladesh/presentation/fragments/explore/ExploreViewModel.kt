package com.btb.explorebangladesh.presentation.fragments.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.btb.explorebangladesh.Resource
import com.btb.explorebangladesh.domain.model.Article
import com.btb.explorebangladesh.domain.repository.BtbRepository
import com.btb.explorebangladesh.lang.LanguageProvider
import com.btb.explorebangladesh.presentation.fragments.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: BtbRepository,
    private val languageProvider: LanguageProvider
) : BaseViewModel() {


    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>>
        get() = _articles

    fun fetchArticles() {
        val language = languageProvider.getCurrentLanguage()
        _isLoading.postValue(true)
        viewModelScope.launch {
            when (val resource = repository.getArticles(
                mapOf(
                    "lang" to language,
                    "title" to "",
                    "categoryId" to "",
                    "page" to 1,
                    "limit" to 10000
                )
            )) {
                is Resource.Failure -> {
                    _isLoading.postValue(false)
                    _error.postValue(resource.text)
                }
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    _isLoading.postValue(false)
                    _articles.postValue(resource.data)
                }
            }
        }
    }

}