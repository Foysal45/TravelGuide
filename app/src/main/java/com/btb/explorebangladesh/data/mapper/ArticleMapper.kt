package com.btb.explorebangladesh.data.mapper

import com.btb.explorebangladesh.data.model.ArticleDetailDto
import com.btb.explorebangladesh.data.model.ArticleDto
import com.btb.explorebangladesh.domain.model.Article
import com.btb.explorebangladesh.domain.model.ArticleDetail

fun ArticleDto.toArticle() = Article(
    address = address ?: "",
    articleType = articleType ?: "",
    categoryId = categoryId ?: emptyList(),
    detail = detail ?: "",
    email = email ?: "",
    id = id,
    lastModifiedDate = lastModifiedDate ?: "",
    medias = medias?.map { it.toMedia() } ?: emptyList(),
    phone = phone ?: "",
    popularityPoints = popularityPoints ?: 0.0,
    rating = rating ?: 0.0f,
    slug = slug ?: "",
    tags = tags?.map { it.toTag() } ?: emptyList(),
    title = title ?: ""
)

fun ArticleDetailDto.toArticleDetail() = ArticleDetail(
    address = address ?: "",
    articleType = articleType ?: "",
    categoryId = categoryId ?: -1,
    category = category?.toCategory(),
    createAt = createAt ?: "",
    createBy = createBy ?: -1,
    deleteAt = deleteAt ?: "",
    deleteBy = deleteBy ?: "",
    email = email ?: "",
    id = id,
    isActive = isActive ?: false,
    isDelete = isDelete ?: false,
    latitude = latitude ?: 0.0,
    longitude = longitude ?: 0.0,
    phone = phone ?: "",
    popularPoint = popularPoint ?: "",
    rating = rating ?: -1,
    scheduleOn = scheduleOn ?: "",
    sections = sections?.map { it.toSection() } ?: emptyList(),
    tags = tags?.map { it.toTag() } ?: emptyList(),
    medias = medias?.map { it.toMedia() } ?: emptyList(),
    slug = slug ?: "",
    updateAt = updateAt ?: "",
    updateBy = updateBy ?: -1
)

