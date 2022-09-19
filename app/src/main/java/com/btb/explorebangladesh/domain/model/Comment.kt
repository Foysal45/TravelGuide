package com.btb.explorebangladesh.domain.model

data class Comment(
    val articleId: Int,
    val articleInfo: ArticleDetail?,
    val user: User?,
    val replay: List<Replay>,
    val comments: String,
    val id: Int,
    val userId: Int
)
