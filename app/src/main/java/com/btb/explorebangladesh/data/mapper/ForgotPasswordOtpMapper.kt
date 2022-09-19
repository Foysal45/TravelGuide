package com.btb.explorebangladesh.data.mapper

import com.btb.explorebangladesh.data.model.ForgotPasswordOtpDto
import com.btb.explorebangladesh.domain.model.ForgotPasswordOtp

fun ForgotPasswordOtpDto.toOtp() = ForgotPasswordOtp(
    otp = otpCode ?: "",
    token = token ?: ""
)