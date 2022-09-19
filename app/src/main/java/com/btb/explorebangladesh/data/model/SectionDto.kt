package com.btb.explorebangladesh.data.model


import com.google.gson.annotations.SerializedName

data class SectionDto(
    @SerializedName("description")
    val description: String?,
    @SerializedName("detailId")
    val detailId: Int?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("media")
    val media: List<MediaDataDto>?,
    @SerializedName("subTitle")
    val subTitle: String?,
    @SerializedName("title")
    val title: String?
)