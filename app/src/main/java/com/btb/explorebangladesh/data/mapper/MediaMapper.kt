package com.btb.explorebangladesh.data.mapper

import com.btb.explorebangladesh.MediaType
import com.btb.explorebangladesh.data.model.MediaDataDto
import com.btb.explorebangladesh.data.model.MediaDto
import com.btb.explorebangladesh.domain.model.Media

fun MediaDto.toMedia(title: String = "") = Media(
    group = group ?: "",
    mediaId = mediaId ?: -1,
    source = source ?: "",
    type = type ?: "",
    title = title
)


fun MediaDataDto.toMedia() = Media(
    group = media?.group ?: "",
    mediaId = mediaId ?: -1,
    source = media?.source ?: "",
    type = media?.type ?: "",
    title = media?.mediaDetail?.title ?: ""
)


fun List<Media>.toIconUrl() = firstOrNull {
    MediaType.hasIcon(it.group)
}?.source

fun List<Media>.toImageUrl() = firstOrNull {
    MediaType.hasImage(it.group, it.type)
}?.source ?: "https://via.placeholder.com/150"

fun List<Media>.hasVideo() = firstOrNull {
    MediaType.hasVideo(it.group, it.type)
} != null