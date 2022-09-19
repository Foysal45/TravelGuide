package com.btb.explorebangladesh.domain.model

data class Replay(
    val articleId: Int,
    val comments: String,
    val id: Int,
    val parentId: Int,
    val userId: Int
)