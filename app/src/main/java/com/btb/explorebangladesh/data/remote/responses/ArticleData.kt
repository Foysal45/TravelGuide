package com.btb.explorebangladesh.data.remote.responses

import com.google.gson.annotations.SerializedName
import com.btb.explorebangladesh.data.model.ArticleDetailDto
import com.btb.explorebangladesh.data.model.ArticleDto

data class ArticleData(
    @SerializedName("articles")
    val articles: List<ArticleDto>?,
    @SerializedName("article")
    val articleDetail: ArticleDetailDto?,
    @SerializedName("wishList")
    val wishes: List<ArticleDto>?
)