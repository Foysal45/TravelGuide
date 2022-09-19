package com.btb.explorebangladesh.presentation.fragments.home.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.btb.explorebangladesh.coil.load
import com.btb.explorebangladesh.databinding.SimpleCommentItemBinding
import com.btb.explorebangladesh.domain.model.Comment
import com.ticonsys.baseadapter.BaseAdapter
import javax.inject.Inject

class CommentAdapter @Inject constructor(
) : BaseAdapter<Comment, SimpleCommentItemBinding>() {

    override fun initializeDiffItemCallback() = object : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment) = oldItem == newItem

    }

    override fun initializeViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = SimpleCommentItemBinding.inflate(layoutInflater, parent, false)

    override fun onBindViewHolder(holder: BaseViewHolder<SimpleCommentItemBinding>, position: Int) {
        val comment = differ.currentList[position]
        holder.binding.apply {
            ivPicture.load(comment.user?.image)
            tvName.text = comment.user?.fullName ?: ""
            tvComment.text = comment.comments
        }
    }
}