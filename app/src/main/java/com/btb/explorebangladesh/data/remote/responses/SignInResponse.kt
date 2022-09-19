package com.btb.explorebangladesh.data.remote.responses

import com.google.gson.annotations.SerializedName
import com.btb.explorebangladesh.data.model.PayloadDto

data class SignInResponse(
    @SerializedName("payload")
    val dto: PayloadDto?
)