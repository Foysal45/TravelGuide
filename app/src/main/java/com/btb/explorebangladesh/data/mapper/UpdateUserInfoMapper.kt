package com.btb.explorebangladesh.data.mapper

import com.btb.explorebangladesh.data.model.user_info.info_update.InfoUpdateRequest
import com.btb.explorebangladesh.domain.model.user_info.info_update.UserUpdateInfo


fun InfoUpdateRequest.toUpdateUserInfo() = UserUpdateInfo(
    address = address ?: "",
    country = country ?: "",
    email = email ?: "",
    fullName = fullName ?: "",
    gender = gender ?: "",
    nationality = nationality ?: "",
    phone = phone ?: "",
    postcode = postcode ?: ""
)
