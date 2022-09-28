package com.btb.explorebangladesh.data.model.user_info.info_update

import com.btb.explorebangladesh.data.model.user_info.CountryInfoDto
import com.google.gson.annotations.SerializedName

data class InfoUpdateRequest(
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("postcode")
    val postcode: String,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("address")
    val address: String,
)