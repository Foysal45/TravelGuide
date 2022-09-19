package com.btb.explorebangladesh.data.model


import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("articleId")
    val articleId: Int?,
    @SerializedName("articleInfo")
    val articleInfo: ArticleDetailDto?,
    @SerializedName("userInfo")
    val user: UserDto?,
    @SerializedName("childId")
    val replay: List<ReplayDto>?,
    @SerializedName("comments")
    val comments: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("userId")
    val userId: Int?
)