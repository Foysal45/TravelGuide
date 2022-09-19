package com.btb.explorebangladesh.presentation.fragments.home.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.btb.explorebangladesh.MediaType
import com.btb.explorebangladesh.activity.createChip
import com.btb.explorebangladesh.databinding.SimpleSectionItemBinding
import com.btb.explorebangladesh.domain.model.Section
import com.btb.explorebangladesh.presentation.fragments.base.BaseAdapter
import com.btb.explorebangladesh.view.hide
import com.btb.explorebangladesh.view.show
import javax.inject.Inject

class SectionAdapter @Inject constructor(
) : BaseAdapter<Section, SimpleSectionItemBinding>() {
    override fun initializeViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = SimpleSectionItemBinding.inflate(layoutInflater, parent, false)

    override fun initializeDiffItemCallback() = object : DiffUtil.ItemCallback<Section>() {
        override fun areItemsTheSame(oldItem: Section, newItem: Section) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Section, newItem: Section) = oldItem == newItem
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<SimpleSectionItemBinding>,
        position: Int
    ) {
        val section = differ.currentList[position]
        val images = section.medias.filter {
            MediaType.hasImage(group = it.group, type = it.type)
        }
        val imageCount = if (images.isNotEmpty()) images.size else 0
        val imageSubCount = if (imageCount > 4) 4 else imageCount

        val links = section.medias.filter {
            MediaType.hasLink(group = it.group, type = it.type)
        }

        holder.binding.apply {
            val context = root.context
            tvSubTitle.text = section.subTitle
            tvDescription.text = section.description

            Log.e("SectionAdapter", "onBindViewHolder: ${section.subTitle}")
            Log.e("SectionAdapter", "onBindViewHolder: ${section.id}")

            if (imageCount > 0) {
                val imageAdapter = ImageGridAdapter(imageCount)
                rvImages.adapter = imageAdapter
                rvImages.show()
                imageAdapter.differ.submitList(images.subList(0, imageSubCount))
            } else {
                rvImages.hide()
            }

            llLink.post {
                llLink.removeAllViews()
                links.forEach {
                    llLink.addView(context.createChip(it.title))
                }
            }

        }
    }
}