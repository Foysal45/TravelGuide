package com.btb.explorebangladesh.data.model.user_info.info_update

import com.google.gson.annotations.SerializedName

data class UserInfoUpdate(
    @SerializedName("payload")
    val payload: InfoUpdateRequest
)