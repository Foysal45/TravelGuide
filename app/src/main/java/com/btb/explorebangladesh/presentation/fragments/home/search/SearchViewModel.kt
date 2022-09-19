package com.btb.explorebangladesh.presentation.fragments.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.btb.explorebangladesh.Resource
import com.btb.explorebangladesh.domain.model.Article
import com.btb.explorebangladesh.domain.model.District
import com.btb.explorebangladesh.domain.repository.BtbRepository
import com.btb.explorebangladesh.lang.LanguageProvider
import com.btb.explorebangladesh.presentation.fragments.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: BtbRepository,
    private val languageProvider: LanguageProvider
) : BaseViewModel() {


    private val _districts = MutableLiveData<List<District>>()
    val districts: LiveData<List<District>>
        get() = _districts

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>>
        get() = _articles


    private val title = MutableLiveData<String>()
    private val categoryId = MutableLiveData<Int>()

    init {
        fetchDistricts()
    }


    private fun fetchDistricts() {
        _districts.postValue(repository.getDistricts())
    }

    fun onSearchEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.TitleChanged -> {
                title.postValue(event.title)
            }
            is SearchEvent.CategoryIdChanged -> {
                categoryId.postValue(event.categoryId)
            }
//            SearchEvent.Search -> {
//                searchArticles()
//            }

        }
    }

    private var pageSize = 20
    fun searchArticles(currentPage: Int = 1) {
        val language = languageProvider.getCurrentLanguage()
        val title = title.value ?: ""
        val categoryId = categoryId.value ?: 0
        _isLoading.postValue(true)
        viewModelScope.launch {
            when (val resource = repository.getArticles(
                mapOf(
                    "lang" to language,
                    "title" to title,
                    "categoryId" to categoryId,
                    "page" to currentPage,
                    "limit" to pageSize
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