package com.btb.explorebangladesh.data.mapper

import com.btb.explorebangladesh.data.model.SectionDto
import com.btb.explorebangladesh.domain.model.Section

fun SectionDto.toSection() = Section(
    description = description ?: "",
    detailId = detailId ?: -1,
    id = id,
    medias = media?.map { it.toMedia() } ?: emptyList(),
    subTitle = subTitle ?: "",
    title = title ?: ""
)