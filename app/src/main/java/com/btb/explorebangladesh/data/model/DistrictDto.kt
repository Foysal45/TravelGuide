package com.btb.explorebangladesh.data.model

import com.google.gson.annotations.SerializedName

data class DistrictDto(
    @SerializedName("bn_name")
    val bnName: String,
    @SerializedName("division_id")
    val divisionId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("long")
    val long: Double?,
    @SerializedName("name")
    val name: String
)