package com.btb.explorebangladesh.data.model.user_info

import com.google.gson.annotations.SerializedName

data class UserInfoDto(
    @SerializedName("address")
    val address: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("countryId")
    val countryId: Int,
    @SerializedName("countryInfo")
    val countryInfoDto: CountryInfoDto = CountryInfoDto(0,"en", 0,""),
    @SerializedName("createAt")
    val createAt: String,
    @SerializedName("createBy")
    val createBy: Int,
    @SerializedName("deleteAt")
    val deleteAt: Any,
    @SerializedName("deleteBy")
    val deleteBy: Any,
    @SerializedName("email")
    val email: String,
    @SerializedName("fbId")
    val fbId: Any,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("gId")
    val gId: Any,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("isActive")
    val isActive: Boolean,
    @SerializedName("isAuth")
    val isAuth: Boolean,
    @SerializedName("isDelete")
    val isDelete: Boolean,
    @SerializedName("joinDt")
    val joinDt: String,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("postcode")
    val postcode: String,
    @SerializedName("socialType")
    val socialType: Any,
    @SerializedName("updateAt")
    val updateAt: String,
    @SerializedName("updateBy")
    val updateBy: Int
)