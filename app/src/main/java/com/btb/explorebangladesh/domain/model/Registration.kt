package com.btb.explorebangladesh.domain.model

data class Registration(
    val otp: String,
    val registrationToken: String,
    val id: Int,
    val email: String
)
