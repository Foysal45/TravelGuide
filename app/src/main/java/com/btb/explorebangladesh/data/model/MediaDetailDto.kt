package com.btb.explorebangladesh.data.model


import com.google.gson.annotations.SerializedName

data class MediaDetailDto(
    @SerializedName("detailId")
    val detailId: Int,
    @SerializedName("languageId")
    val languageId: Int?,
    @SerializedName("title")
    val title: String?
)