package com.btb.explorebangladesh.domain.repository

import androidx.lifecycle.LiveData
import com.btb.explorebangladesh.Resource
import com.btb.explorebangladesh.StaticPage
import com.btb.explorebangladesh.data.model.CommentDto
import com.btb.explorebangladesh.data.remote.responses.CommentData
import com.btb.explorebangladesh.data.remote.responses.StaticData
import com.btb.explorebangladesh.domain.model.*
import com.btb.explorebangladesh.responses.ApiResponse
import com.btb.explorebangladesh.responses.ResponseDTO


interface BtbRepository {

    fun getDistricts(): List<District>

    fun getCategories(
        queryMap: Map<String, Int>
    ): LiveData<Resource<List<Category>>>

    suspend fun getArticles(
        queryMap: Map<String, Any>
    ): Resource<List<Article>>

    suspend fun getArticleDetail(
        id: Int, lang: String
    ): Resource<ArticleDetail>

    suspend fun isWishListed(
        id: Int
    ): Resource<Boolean>

    suspend fun updateWishListed(
        id: Int,
        body: Map<String, Int>
    ): Resource<Boolean>

    suspend fun shareArticle(
        id: Int,
        body: Map<String, Int>
    ): Resource<Boolean>

    suspend fun getWishList(
        queryMap: Map<String, Any>
    ): Resource<List<Article>>

    suspend fun getStaticDetail(
        page: StaticPage, lang: String
    ): Resource<String>

    suspend fun getComments(
        queryMap: Map<String, Int>
    ): Resource<List<Comment>>

    suspend fun createComment(
        comment: String,
        articleId: Int
    ): Resource<Comment>

}