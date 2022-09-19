package com.btb.explorebangladesh.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val parentId: Int,
    val medias: List<Media>,
    val slug: String,
    val title: String,
    val name: String,
    val version: Long
) : Parcelable