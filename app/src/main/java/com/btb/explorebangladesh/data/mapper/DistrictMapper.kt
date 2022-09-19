package com.btb.explorebangladesh.data.mapper

import com.btb.explorebangladesh.data.model.DistrictDto
import com.btb.explorebangladesh.domain.model.District

fun DistrictDto.toDistrict() = District(
    bnName = bnName,
    divisionId = divisionId,
    id = id,
    lat = lat ?: 0.0,
    long = long ?: 0.0,
    name = name
)