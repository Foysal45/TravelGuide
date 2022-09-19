package com.btb.explorebangladesh.data.remote.request

import com.google.gson.annotations.SerializedName

data class SocialLoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("socialType")
    val socialType: String,
    @SerializedName("picture")
    val profileUrl: String,
    @SerializedName("accessToken")
    val accessToken: String
)