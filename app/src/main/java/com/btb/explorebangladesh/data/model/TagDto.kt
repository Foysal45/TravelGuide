package com.btb.explorebangladesh.data.model


import com.google.gson.annotations.SerializedName

data class TagDto(
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("tagId")
    val tagId: Int,
    @SerializedName("text")
    val text: String?
)