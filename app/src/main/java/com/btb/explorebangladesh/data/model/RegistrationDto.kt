package com.btb.explorebangladesh.data.model


import com.google.gson.annotations.SerializedName

data class RegistrationDto(
    @SerializedName("otp")
    val otp: String?,
    @SerializedName("registrationToken")
    val registrationToken: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("email")
    val email: String?
)