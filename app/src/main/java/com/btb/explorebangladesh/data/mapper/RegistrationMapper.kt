package com.btb.explorebangladesh.data.mapper

import com.btb.explorebangladesh.data.model.RegistrationDto
import com.btb.explorebangladesh.domain.model.Registration

fun RegistrationDto.toRegistration() = Registration(
    otp = otp ?: "",
    registrationToken = registrationToken ?: "",
    id = id ?: -1,
    email = email ?: ""
)