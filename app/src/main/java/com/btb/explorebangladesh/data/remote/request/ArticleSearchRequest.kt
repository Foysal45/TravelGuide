package com.btb.explorebangladesh.data.remote.request

import com.google.gson.annotations.SerializedName

data class ArticleSearchRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("lang")
    val lang: String,
    @SerializedName("page")
    val page: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("hashTagId")
    val hashTagId: Int? = null,
    @SerializedName("skipArticleId")
    val skipArticleId: Int? = null
)