package com.btb.explorebangladesh.domain.model

data class ForgotPasswordOtp(
    val otp: String,
    val token: String
)