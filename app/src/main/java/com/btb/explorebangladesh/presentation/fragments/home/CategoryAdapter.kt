package com.btb.explorebangladesh.presentation.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.btb.explorebangladesh.coil.load
import com.btb.explorebangladesh.data.mapper.toIconUrl
import com.btb.explorebangladesh.databinding.SimpleCategoryItemBinding
import com.btb.explorebangladesh.domain.model.Category
import com.btb.explorebangladesh.presentation.fragments.base.BaseAdapter
import javax.inject.Inject


class CategoryAdapter @Inject constructor(
) : BaseAdapter<Category, SimpleCategoryItemBinding>() {


    override fun initializeViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = SimpleCategoryItemBinding.inflate(layoutInflater, parent, false)

    override fun initializeDiffItemCallback() = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category) = oldItem == newItem
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<SimpleCategoryItemBinding>,
        position: Int
    ) {
        val category = differ.currentList[position]
        holder.binding.apply {
            val iconUrl = category.medias.toIconUrl()
            ivIcon.load(iconUrl)
//            tvName.text = category.title
            root.setOnClickListener { view ->
                listener?.let { click ->
                    click(view, category)
                }
            }
        }
    }
}