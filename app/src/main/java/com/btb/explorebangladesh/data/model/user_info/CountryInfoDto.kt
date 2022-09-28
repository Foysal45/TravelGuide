package com.btb.explorebangladesh.data.model.user_info

import com.google.gson.annotations.SerializedName

data class CountryInfoDto(
    @SerializedName("countryId")
    val countryId: Int,
    @SerializedName("lang")
    val lang: String,
    @SerializedName("languageId")
    val languageId: Int,
    @SerializedName("title")
    val title: String
)