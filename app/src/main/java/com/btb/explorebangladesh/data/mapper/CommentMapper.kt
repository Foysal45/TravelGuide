package com.btb.explorebangladesh.data.mapper

import com.btb.explorebangladesh.data.model.CommentDto
import com.btb.explorebangladesh.data.model.ReplayDto
import com.btb.explorebangladesh.domain.model.Comment
import com.btb.explorebangladesh.domain.model.Replay

fun CommentDto.toComment() = Comment(
    articleId = articleId ?: -1,
    articleInfo = articleInfo?.toArticleDetail(),
    user = user?.toUser(),
    replay = replay?.map { it.toReplay() } ?: emptyList(),
    comments = comments ?: "",
    id = id,
    userId = userId ?: -1
)

fun ReplayDto.toReplay() = Replay(
    articleId = articleId ?: -1,
    comments = comments ?: "",
    id = id,
    parentId = parentId ?: -1,
    userId = userId ?: -1
)