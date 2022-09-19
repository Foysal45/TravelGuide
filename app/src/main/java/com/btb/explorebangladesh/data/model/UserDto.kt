package com.btb.explorebangladesh.data.model


import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("email")
    val email: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("id")
    val id: Int
)