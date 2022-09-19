package com.btb.explorebangladesh.presentation.fragments.home.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.btb.explorebangladesh.coil.load
import com.btb.explorebangladesh.data.mapper.toImageUrl
import com.btb.explorebangladesh.databinding.SimpleRelatedArticleItemBinding
import com.btb.explorebangladesh.domain.model.Article
import com.btb.explorebangladesh.presentation.fragments.base.BaseAdapter
import javax.inject.Inject

class RelatedArticleAdapter @Inject constructor(
) : BaseAdapter<Article, SimpleRelatedArticleItemBinding>() {
    override fun initializeViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = SimpleRelatedArticleItemBinding.inflate(layoutInflater, parent, false)

    override fun initializeDiffItemCallback() = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<SimpleRelatedArticleItemBinding>,
        position: Int
    ) {
        val article = differ.currentList[position]
        val image = article.medias.toImageUrl()
        holder.binding.apply {
            tvArticleTitle.text = article.title
            tvArticleAddress.text = article.address
            ivArticleImage.load(image)
            root.setOnClickListener { view ->
                listener?.let { click ->
                    click(view, article)
                }
            }
        }
    }
}