package com.btb.explorebangladesh.data.remote.responses

import com.btb.explorebangladesh.data.model.WishDto
import com.google.gson.annotations.SerializedName

data class WishData(
    @SerializedName("wish")
    val wish: WishDto?
)