package com.btb.explorebangladesh.data.model


import com.google.gson.annotations.SerializedName

data class ArticleDetailDto(
    @SerializedName("address")
    val address: String?,
    @SerializedName("articleType")
    val articleType: String?,
    @SerializedName("categoryId")
    val categoryId: Int?,
    @SerializedName("categoryInfo")
    val category: CategoryDto?,
    @SerializedName("createAt")
    val createAt: String?,
    @SerializedName("createBy")
    val createBy: Int?,
    @SerializedName("deleteAt")
    val deleteAt: String?,
    @SerializedName("deleteBy")
    val deleteBy: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isActive")
    val isActive: Boolean?,
    @SerializedName("isDelete")
    val isDelete: Boolean?,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("popularPoint")
    val popularPoint: String?,
    @SerializedName("rating")
    val rating: Int?,
    @SerializedName("schedule_on")
    val scheduleOn: String?,
    @SerializedName("sections")
    val sections: List<SectionDto>?,
    @SerializedName("tags")
    val tags: List<TagDto>?,
    @SerializedName("media")
    val medias: List<MediaDataDto>?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("updateAt")
    val updateAt: String?,
    @SerializedName("updateBy")
    val updateBy: Int?
)