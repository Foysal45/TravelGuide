package com.btb.explorebangladesh.data.model

import com.google.gson.annotations.SerializedName

data class ForgotPasswordOtpDto(
    @SerializedName("otp")
    val otpCode: String?,
    @SerializedName("token")
    val token: String?
)