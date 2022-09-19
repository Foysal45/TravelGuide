package com.btb.explorebangladesh.data.model


import com.google.gson.annotations.SerializedName

data class WishDto(
    @SerializedName("articleId")
    val articleId: Int?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("userId")
    val userId: Int?
)