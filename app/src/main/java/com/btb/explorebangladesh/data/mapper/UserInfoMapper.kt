package com.btb.explorebangladesh.data.mapper

import com.btb.explorebangladesh.data.model.user_info.UserInfoDto
import com.btb.explorebangladesh.domain.model.user_info.UserInfo


fun UserInfoDto.toUserInfo() = UserInfo(
    address = address ?: "",
    country = country ?: "",
    countryId = countryId ?: 0,
    countryInfo = countryInfoDto,
     createAt = createAt ?: "",
    createBy = createBy ?: 0,
    deleteAt =  deleteAt ?: "",
    deleteBy =  deleteBy ?: "",
    email = email ?: "",
    fbId = fbId ?: "",
    fullName = fullName ?: "",
    gId = gId ?: "" ,
    gender = gender ?: "",
    id = id ?: 0,
    image = image ?: "",
    isActive = isActive ?: false,
    isAuth =isAuth ?: false,
    isDelete = isDelete ?: false,
    joinDt = joinDt ?: "",
    nationality = nationality ?: "",
    phone = phone ?: "",
    postcode = postcode ?: "",
    socialType = socialType ?: "",
    updateAt =updateAt ?: "",
    updateBy = updateBy ?: 0
)
