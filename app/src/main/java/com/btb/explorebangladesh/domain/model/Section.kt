package com.btb.explorebangladesh.domain.model

data class Section(
    val description: String,
    val detailId: Int,
    val id: Int,
    val medias: List<Media>,
    val subTitle: String,
    val title: String
)