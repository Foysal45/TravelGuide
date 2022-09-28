package com.btb.explorebangladesh.domain.model.user_info

import com.btb.explorebangladesh.data.model.user_info.CountryInfoDto

data class UserInfo(
    val address: String,
    val country: String,
    val countryId: Int,
    val countryInfo: CountryInfoDto,
    val createAt: String,
    val createBy: Int,
    val deleteAt: Any,
    val deleteBy: Any,
    val email: String,
    val fbId: Any,
    val fullName: String,
    val gId: Any,
    val gender: String,
    val id: Int,
    val image: String,
    val isActive: Boolean,
    val isAuth: Boolean,
    val isDelete: Boolean,
    val joinDt: String,
    val nationality: String,
    val phone: String,
    val postcode: String,
    val socialType: Any,
    val updateAt: String,
    val updateBy: Int
)