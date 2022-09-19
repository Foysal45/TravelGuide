package com.btb.explorebangladesh.data.mapper

import com.btb.explorebangladesh.data.model.CategoryDto
import com.btb.explorebangladesh.domain.model.Category

fun CategoryDto.toCategory() = Category(
    id = id,
    parentId = parentId ?: -1,
    medias = medias?.map { it.toMedia() } ?: emptyList(),
    slug = slug ?: "",
    title = title ?: "",
    name = name ?: "",
    version = version ?: 0L
)