package com.btb.explorebangladesh.data.model

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("parent_id")
    val parentId: Int?,
    @SerializedName("last_modified_date")
    val lastModifiedDate: String?,
    @SerializedName("mediaList")
    val medias: List<MediaDto>?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("_version_")
    val version: Long?
)