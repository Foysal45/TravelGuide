package com.btb.explorebangladesh.data.remote.responses

import com.google.gson.annotations.SerializedName
import com.btb.explorebangladesh.data.model.CategoryDto

data class CategoryData(
    @SerializedName("categories")
    val categories: List<CategoryDto>
)