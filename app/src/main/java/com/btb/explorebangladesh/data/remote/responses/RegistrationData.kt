package com.btb.explorebangladesh.data.remote.responses

import com.google.gson.annotations.SerializedName
import com.btb.explorebangladesh.data.model.RegistrationDto

data class RegistrationData(
    @SerializedName("registration")
    val registration: RegistrationDto
)
