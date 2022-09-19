package com.btb.explorebangladesh.presentation.fragments.home.detail

import android.util.Log
import androidx.lifecycle.*
import com.btb.explorebangladesh.Resource
import com.btb.explorebangladesh.domain.model.Article
import com.btb.explorebangladesh.domain.model.ArticleDetail
import com.btb.explorebangladesh.domain.model.Comment
import com.btb.explorebangladesh.domain.repository.BtbRepository
import com.btb.explorebangladesh.lang.LanguageProvider
import com.btb.explorebangladesh.presentation.fragments.base.BaseViewModel
import com.btb.explorebangladesh.util.UserFactory
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: BtbRepository,
    private val userFactory: UserFactory,
    private val languageProvider: LanguageProvider
) : BaseViewModel() {

    val latLng = MutableLiveData<LatLng>()


    fun hasLoggedIn(): Boolean = userFactory.getAccessToken().isNotEmpty()

    val ratingMessages = arrayOf(
        "Useless",
        "Useless+",
        "Poor",
        "Poor+",
        "Ok",
        "Ok+",
        "Good",
        "Good+",
        "Excellent",
        "Excellent+"
    )

    var slug = ""

    val language: String
        get() = languageProvider.getCurrentLanguage()

    private val _articleDetail = MutableLiveData<ArticleDetail>()
    val articleDetail: LiveData<ArticleDetail>
        get() = _articleDetail


    private val _relatedArticles = MutableLiveData<List<Article>>()
    val relatedArticles: LiveData<List<Article>>
        get() = _relatedArticles


    fun getArticleDetail(articleId: Int) {
        updateLoading(true)
        viewModelScope.launch {
            when (val resource = repository.getArticleDetail(
                id = articleId,
                lang = languageProvider.getCurrentLanguage()
            )) {
                is Resource.Failure -> {
                    updateLoading(false)
                    _error.postValue(resource.text)
                }
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    updateLoading(false)
                    slug = resource.data.slug
                    _articleDetail.postValue(resource.data)
                }
            }
        }
    }

    fun relatedArticles(articleId: Int) {
        updateLoading(true)
        viewModelScope.launch {
            when (val resource = repository.getArticles(mapOf("skipArticleId" to articleId))) {
                is Resource.Failure -> {
                    updateLoading(false)
                    _error.postValue(resource.text)
                }
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    updateLoading(false)
                    _relatedArticles.postValue(resource.data)
                }
            }
        }
    }

    private val _isWishListed = MutableLiveData<Boolean>()
    val isWishListed: LiveData<Boolean>
        get() = _isWishListed

    fun wishListedStatus(articleId: Int) {
        viewModelScope.launch {
            when (val resource = repository.isWishListed(articleId)) {
                is Resource.Failure -> {
                    updateLoading(false)
                    _isWishListed.postValue(false)
                }
                is Resource.Loading -> updateLoading(true)
                is Resource.Success -> {
                    updateLoading(false)
                    _isWishListed.postValue(resource.data)
                }
            }
        }
    }

    fun updateWishListed(articleId: Int) {
        val isWishListedStatus = isWishListed.value ?: false
        val type = if (isWishListedStatus) 0 else 1
        viewModelScope.launch {
            when (val resource = repository.updateWishListed(articleId, mapOf("type" to type))) {
                is Resource.Failure -> {
                    updateLoading(false)
                    _isWishListed.postValue(false)
                }
                is Resource.Loading -> updateLoading(true)
                is Resource.Success -> {
                    updateLoading(false)
                    Log.e(TAG, "updateWishListed: ${resource.data}")
                    _isWishListed.postValue(resource.data)
                }
            }
        }
    }

    fun shareArticle(articleId: Int) {
        viewModelScope.launch {
            when (repository.shareArticle(articleId, mapOf("type" to 1))) {
                is Resource.Failure -> updateLoading(false)
                is Resource.Loading -> updateLoading(true)
                is Resource.Success -> updateLoading(false)
            }
        }
    }


    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>>
        get() = _comments

    fun getArticleComments(articleId: Int) {
        viewModelScope.launch {
            when (val resource = repository.getComments(
                mapOf(
                    "page" to 1, "limit" to 1000, "articleId" to articleId
                )
            )) {
                is Resource.Failure -> {
                    _error.postValue(resource.text)
                    updateLoading(false)
                }
                is Resource.Loading -> updateLoading(true)
                is Resource.Success -> {
                    updateLoading(false)
                    _comments.postValue(resource.data)
                }
            }
        }
    }

    fun createComment(comment: String, articleId: Int) {
        viewModelScope.launch {
            when (repository.createComment(comment, articleId)) {
                is Resource.Failure -> updateLoading(false)
                is Resource.Loading -> updateLoading(true)
                is Resource.Success -> {
                    updateLoading(false)
                    getArticleComments(articleId)
                }
            }
        }
    }

}