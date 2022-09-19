package com.btb.explorebangladesh.data.model

import com.google.gson.annotations.SerializedName

data class MediaDataDto(
    @SerializedName("mediaId")
    val mediaId: Int?,
    @SerializedName("mediaInfo")
    val media: MediaDto?
)