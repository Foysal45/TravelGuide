package com.btb.explorebangladesh.data.remote.source


import com.btb.explorebangladesh.StaticPage
import com.btb.explorebangladesh.data.model.CommentDto
import com.btb.explorebangladesh.data.remote.responses.*
import com.btb.explorebangladesh.responses.ApiResponse
import com.btb.explorebangladesh.responses.ResponseDTO
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


interface BtbDataSource {

    suspend fun getCategories(
        queryMap: Map<String, Int>
    ): ApiResponse<ResponseDTO<CategoryData>>

    suspend fun getArticles(
        queryMap: Map<String, Any>
    ): ApiResponse<ResponseDTO<ArticleData>>

    suspend fun getArticleDetail(
        id: Int, lang: String
    ): ApiResponse<ResponseDTO<ArticleData>>

    suspend fun isWishListed(
        id: Int
    ): ApiResponse<ResponseDTO<WishData>>

    suspend fun updateWishListed(
        id: Int,
        body: Map<String, Int>
    ): ApiResponse<ResponseDTO<Any>>

    suspend fun shareArticle(
        id: Int,
        body: Map<String, Int>
    ): ApiResponse<ResponseDTO<Any>>

    suspend fun getWishList(
        queryMap: Map<String, Any>
    ): ApiResponse<ResponseDTO<ArticleData>>


    suspend fun getStaticDetail(
        page: String, lang: String
    ): ApiResponse<ResponseDTO<StaticData>>

    suspend fun getComments(
        queryMap: Map<String, Int>
    ): ApiResponse<ResponseDTO<CommentData>>

    suspend fun createComment(
        comment: String,
        articleId: Int
    ): ApiResponse<ResponseDTO<CommentDto>>

}