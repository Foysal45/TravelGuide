package com.btb.explorebangladesh.presentation.fragments.home.search.result

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.btb.explorebangladesh.coil.load
import com.btb.explorebangladesh.data.mapper.toImageUrl
import com.btb.explorebangladesh.data.mapper.toTags
import com.btb.explorebangladesh.databinding.SimpleArticleItemBinding
import com.btb.explorebangladesh.domain.model.Article
import com.btb.explorebangladesh.presentation.fragments.base.BaseAdapter
import com.btb.explorebangladesh.view.hide
import com.btb.explorebangladesh.view.show
import javax.inject.Inject

class ArticleAdapter @Inject constructor(
) : BaseAdapter<Article, SimpleArticleItemBinding>() {
    override fun initializeViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = SimpleArticleItemBinding.inflate(layoutInflater, parent, false)

    override fun initializeDiffItemCallback() = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
    }

    override fun onBindViewHolder(holder: BaseViewHolder<SimpleArticleItemBinding>, position: Int) {
        val article = differ.currentList[position]
        holder.binding.apply {
            val imageUrl = article.medias.toImageUrl()
            ivArticleImage.load(imageUrl)

            tvPlaceName.text = article.title
            tvPlaceType.text = article.articleType
            tvTags.text = article.tags.toTags()
            val rating = if (article.rating > 0) {
                article.rating.toFloat()
            } else {
                0.0f
            }
            rbRating.rating = rating

            root.setOnClickListener { view ->
                listener?.let { click ->
                    click(view, article)
                }
            }

        }
    }
}