package com.btb.explorebangladesh.data.remote.request

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("phone")
    val phoneNumber: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
