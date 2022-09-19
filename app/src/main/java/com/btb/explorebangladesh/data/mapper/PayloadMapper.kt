package com.btb.explorebangladesh.data.mapper

import com.btb.explorebangladesh.data.model.PayloadDto
import com.btb.explorebangladesh.domain.model.Payload

fun PayloadDto.toPayload() = Payload(
    id = id,
    accessToken = accessToken ?: ""
)