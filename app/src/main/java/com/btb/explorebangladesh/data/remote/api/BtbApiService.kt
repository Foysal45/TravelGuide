package com.btb.explorebangladesh.data.remote.api

import com.btb.explorebangladesh.data.model.CommentDto
import com.btb.explorebangladesh.data.remote.responses.*
import com.btb.explorebangladesh.responses.ResponseDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface BtbApiService {

    @GET("categories")
    suspend fun getCategories(
        @QueryMap queryMap: Map<String, Int>
    ): Response<ResponseDTO<CategoryData>>

    @GET("articles")
    suspend fun getArticles(
        @QueryMap queryMap: Map<String, @JvmSuppressWildcards Any>
    ): Response<ResponseDTO<ArticleData>>


    @GET("articles/{id}")
    suspend fun getArticleDetail(
        @Path("id") id: Int,
        @Query("lang") lang: String
    ): Response<ResponseDTO<ArticleData>>

    @GET("articles/{id}/wish")
    suspend fun isWishListed(
        @Path("id") id: Int
    ): Response<ResponseDTO<WishData>>

    @POST("articles/{id}/wish")
    suspend fun updateWishListed(
        @Path("id") id: Int,
        @Body body: Map<String, Int>
    ): Response<ResponseDTO<Any>>

    @POST("articles/{id}/share")
    suspend fun shareArticle(
        @Path("id") id: Int,
        @Body body: Map<String, Int>
    ): Response<ResponseDTO<Any>>

    @GET("articles/wishlist")
    suspend fun getWishList(
        @QueryMap queryMap: Map<String, @JvmSuppressWildcards Any>
    ): Response<ResponseDTO<ArticleData>>


    @GET("static/{page}")
    suspend fun getStaticDetail(
        @Path("page") page: String,
        @Query("lang") lang: String
    ): Response<ResponseDTO<StaticData>>


    @GET("comments/article-wise-comment")
    suspend fun getComments(
        @QueryMap queryMap: Map<String, Int>
    ): Response<ResponseDTO<CommentData>>

    @POST("comments/create")
    @FormUrlEncoded
    suspend fun createComment(
        @Field("comments") comment: String,
        @Field("articleId") articleId: Int
    ): Response<ResponseDTO<CommentDto>>


}