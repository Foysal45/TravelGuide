package com.btb.explorebangladesh.data.model


import com.google.gson.annotations.SerializedName

data class ReplayDto(
    @SerializedName("articleId")
    val articleId: Int?,
    @SerializedName("comments")
    val comments: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("parentId")
    val parentId: Int?,
    @SerializedName("userId")
    val userId: Int?
)