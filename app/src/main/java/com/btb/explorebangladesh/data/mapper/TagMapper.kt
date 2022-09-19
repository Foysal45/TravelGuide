package com.btb.explorebangladesh.data.mapper

import com.btb.explorebangladesh.data.model.TagDto
import com.btb.explorebangladesh.domain.model.Tag

fun TagDto.toTag() = Tag(
    slug = slug ?: "",
    tagId = tagId,
    text = text ?: ""
)

fun List<Tag>.toTags() = map { "#${it.text}" }.joinToString(separator = " ").trim()