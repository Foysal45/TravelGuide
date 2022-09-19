package com.btb.explorebangladesh.presentation.fragments.home.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.btb.explorebangladesh.databinding.SimpleReplayItemBinding
import com.btb.explorebangladesh.domain.model.Replay
import com.btb.explorebangladesh.presentation.fragments.base.BaseAdapter

class ReplayAdapter : BaseAdapter<Replay, SimpleReplayItemBinding>() {
    override fun initializeViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = SimpleReplayItemBinding.inflate(layoutInflater, parent, false)

    override fun initializeDiffItemCallback() = object : DiffUtil.ItemCallback<Replay>() {
        override fun areItemsTheSame(oldItem: Replay, newItem: Replay) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Replay, newItem: Replay) = oldItem == newItem

    }

    override fun onBindViewHolder(holder: BaseViewHolder<SimpleReplayItemBinding>, position: Int) {
        val replay = differ.currentList[position]
        holder.binding.apply {
            tvName.text = "Replayer Name"
            tvComment.text = replay.comments
        }
    }
}