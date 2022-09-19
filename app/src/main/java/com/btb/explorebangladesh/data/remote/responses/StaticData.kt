package com.btb.explorebangladesh.data.remote.responses

import com.google.gson.annotations.SerializedName

data class StaticData(
    @SerializedName("abouts")
    val aboutUs: String?,
    @SerializedName("tramsCondition")
    val tramsCondition: String?,
    @SerializedName("privacyPolicy")
    val privacyPolicy: String?
)