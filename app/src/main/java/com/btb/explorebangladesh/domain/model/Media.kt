package com.btb.explorebangladesh.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Media(
    val group: String,
    val mediaId: Int,
    val source: String,
    val type: String,
    val title: String
): Parcelable