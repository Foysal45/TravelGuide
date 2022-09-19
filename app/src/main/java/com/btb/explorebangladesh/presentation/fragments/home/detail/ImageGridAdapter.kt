package com.btb.explorebangladesh.presentation.fragments.home.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.btb.explorebangladesh.coil.load
import com.btb.explorebangladesh.databinding.SimpleImageGridItemBinding
import com.btb.explorebangladesh.domain.model.Media
import com.btb.explorebangladesh.presentation.fragments.base.BaseAdapter
import com.btb.explorebangladesh.view.show

class ImageGridAdapter(
    private val imageCount: Int
) : BaseAdapter<Media, SimpleImageGridItemBinding>() {
    override fun initializeViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = SimpleImageGridItemBinding.inflate(layoutInflater, parent, false)

    override fun initializeDiffItemCallback() = object : DiffUtil.ItemCallback<Media>() {
        override fun areItemsTheSame(oldItem: Media, newItem: Media) =
            oldItem.mediaId == newItem.mediaId

        override fun areContentsTheSame(oldItem: Media, newItem: Media) = oldItem == newItem
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<SimpleImageGridItemBinding>,
        position: Int
    ) {
        val media = differ.currentList[position]
        holder.binding.apply {
            ivImage.load(media.source)
            if (position == 3) {
                val count = "+${imageCount - 4}"
                tvImageCount.show()
                tvImageCount.text = count
            }
        }
    }
}