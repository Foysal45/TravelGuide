package com.btb.explorebangladesh.data.model

import com.google.gson.annotations.SerializedName

data class PayloadDto(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("id")
    val id: Int
)