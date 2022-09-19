package com.btb.explorebangladesh.data.model

import com.google.gson.annotations.SerializedName

data class MediaDto(
    @SerializedName("group")
    val group: String?,
    @SerializedName("mediaId")
    val mediaId: Int?,
    @SerializedName("source")
    val source: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("mediaDetail")
    val mediaDetail: MediaDetailDto?
)