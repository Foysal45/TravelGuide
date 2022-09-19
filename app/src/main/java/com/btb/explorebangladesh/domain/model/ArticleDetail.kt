package com.btb.explorebangladesh.domain.model

data class ArticleDetail(
    val address: String,
    val articleType: String,
    val categoryId: Int,
    val category: Category?,
    val createAt: String,
    val createBy: Int,
    val deleteAt: String,
    val deleteBy: String,
    val email: String,
    val id: Int,
    val isActive: Boolean,
    val isDelete: Boolean,
    val latitude: Double,
    val longitude: Double,
    val phone: String,
    val popularPoint: String,
    val rating: Int,
    val scheduleOn: String,
    val sections: List<Section>,
    val slug: String,
    val updateAt: String,
    val updateBy: Int,
    val tags: List<Tag>,
    val medias: List<Media>
)