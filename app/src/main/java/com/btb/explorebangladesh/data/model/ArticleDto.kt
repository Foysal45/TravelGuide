package com.btb.explorebangladesh.data.model


import com.google.gson.annotations.SerializedName

data class ArticleDto(
    @SerializedName("address")
    val address: String?,
    @SerializedName("articleType")
    val articleType: String?,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("categoryId")
    val categoryId: List<Int>?,
    @SerializedName("last_modified_date")
    val lastModifiedDate: String?,
    @SerializedName("mediaList")
    val medias: List<MediaDto>?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("popularityPoints")
    val popularityPoints: Double?,
    @SerializedName("rating")
    val rating: Float?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("tagsDetailsList")
    val tags: List<TagDto>?,
    @SerializedName("title")
    val title: String?
)