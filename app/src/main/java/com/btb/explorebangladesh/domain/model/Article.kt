package com.btb.explorebangladesh.domain.model

data class Article(
    val address: String,
    val articleType: String,
    val categoryId: List<Int>,
    val detail: String,
    val email: String,
    val id: Int,
    val lastModifiedDate: String,
    val medias: List<Media>,
    val phone: String,
    val popularityPoints: Double,
    val rating: Float,
    val slug: String,
    val tags: List<Tag>,
    val title: String
)