package com.btb.explorebangladesh.data.remote.responses

import com.btb.explorebangladesh.data.model.CommentDto
import com.google.gson.annotations.SerializedName

data class CommentData(
    @SerializedName("commentList")
    val comments: List<CommentDto>?
)