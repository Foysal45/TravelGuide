package com.btb.explorebangladesh.data.mapper

import com.btb.explorebangladesh.data.model.UserDto
import com.btb.explorebangladesh.domain.model.User

fun UserDto.toUser() = User(
    id = id,
    fullName = fullName ?: "",
    email = email ?: "",
    image = image ?: ""
)