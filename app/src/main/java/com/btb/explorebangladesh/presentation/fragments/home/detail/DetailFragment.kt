package com.btb.explorebangladesh.presentation.fragments.home.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.activity.share
import com.btb.explorebangladesh.activity.startAndFinish
import com.btb.explorebangladesh.activity.toast
import com.btb.explorebangladesh.coil.load
import com.btb.explorebangladesh.data.mapper.hasVideo
import com.btb.explorebangladesh.data.mapper.toImageUrl
import com.btb.explorebangladesh.databinding.FragmentDetailBinding
import com.btb.explorebangladesh.domain.model.ArticleDetail
import com.btb.explorebangladesh.domain.model.Comment
import com.btb.explorebangladesh.domain.model.Section
import com.btb.explorebangladesh.presentation.activities.auth.AuthActivity
import com.btb.explorebangladesh.presentation.fragments.base.BaseFragment
import com.btb.explorebangladesh.view.hide
import com.btb.explorebangladesh.view.show
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailViewModel, FragmentDetailBinding>(
    R.layout.fragment_detail
) {

    private val args by navArgs<DetailFragmentArgs>()

    private var googleMap: GoogleMap? = null

    private var articleId: Int = 0

    @Inject
    lateinit var sectionAdapter: SectionAdapter

    @Inject
    lateinit var relatedArticleAdapter: RelatedArticleAdapter

    @Inject
    lateinit var commentAdapter: CommentAdapter

    override val viewModel by viewModels<DetailViewModel>()

    override fun initializeViewBinding(view: View) = FragmentDetailBinding.bind(view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        articleId = args.articleId
        Log.e(TAG, "onCreate: $articleId")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.mapView.onCreate(savedInstanceState)
//        binding.mapView.getMapAsync {
//            MapsInitializer.initialize(requireContext())
//            googleMap = it
//            updatePosition()
//        }

        setupRecyclerView()
        fetchArticleInfo()
        subscribeObservers()
        setupListeners()

        updateCommentInputStatus()

    }

    private fun updatePosition() {
        viewModel.latLng.observe(viewLifecycleOwner){ latLng ->
            latLng ?: return@observe
            googleMap?.apply {
                addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title("")
                )
                moveCamera(CameraUpdateFactory.newLatLng(latLng))
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
//        binding.mapView.onPause()
    }

    override fun onStart() {
        super.onStart()
//        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
//        binding.mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
//        binding.mapView.onLowMemory()
    }


    private fun updateCommentInputStatus() {
        if (viewModel.hasLoggedIn()) {
            binding.llCommentInput.show()
        } else {
            binding.llCommentInput.hide()
        }
    }

    private fun fetchArticleInfo() {
        viewModel.getArticleDetail(articleId)
        viewModel.relatedArticles(articleId)
        viewModel.wishListedStatus(articleId)
        viewModel.getArticleComments(articleId)
    }

    private fun setupRecyclerView() {
        binding.rvSections.adapter = sectionAdapter

        binding.rvRelated.adapter = relatedArticleAdapter
        relatedArticleAdapter.setOnItemClickListener { _, item ->
            articleId = item.id
            fetchArticleInfo()
        }

        binding.rvComment.adapter = commentAdapter

    }

    private fun setupListeners() {
        binding.ivPlay.setOnClickListener {
            // Play video
        }

        binding.ivShare.setOnClickListener {
            viewModel.shareArticle(articleId)
            requireContext().share(
                articleId = articleId,
                slug = viewModel.slug,
                lang = viewModel.language
            )
        }

        binding.ivLike.setOnClickListener {
            if (viewModel.hasLoggedIn()) {
                viewModel.updateWishListed(articleId)
            } else {
                navigateToAuthActivity()
            }
        }

        binding.rbRating.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (rating < 0.5) {
                return@setOnRatingBarChangeListener
            }
            updateRatingComments(rating)
            if (fromUser) {
                // Update Rating in the server
                if (viewModel.hasLoggedIn()) {

                } else {
                    navigateToAuthActivity()
                }
            }
        }

        binding.ivSend.setOnClickListener {
            val comment = binding.etComment.text.toString()
            if (comment.isEmpty()) {
                requireContext().toast(R.string.enter_comment)
                return@setOnClickListener
            }
            binding.etComment.setText("")
            viewModel.createComment(comment, articleId)
        }


    }

    private fun updateRatingComments(rating: Float) {
        val ratingIndex = ((rating * 2).toInt() - 1) % viewModel.ratingMessages.size
        binding.tvComment.text = viewModel.ratingMessages[ratingIndex]
    }

    private fun subscribeObservers() {
        viewModel.articleDetail.observe(viewLifecycleOwner) { detail ->
            detail ?: return@observe
            updateArticleDetail(detail)
            viewModel.latLng.postValue(
                LatLng(
                    detail.latitude,
                    detail.longitude
                )
            )
        }

        viewModel.relatedArticles.observe(viewLifecycleOwner) { articles ->
            articles ?: return@observe
            relatedArticleAdapter.differ.submitList(articles)
        }

        viewModel.isWishListed.observe(viewLifecycleOwner) { isWishListed ->
            updateWishImage(isWishListed)
        }

        viewModel.comments.observe(viewLifecycleOwner) { comments ->
            comments ?: return@observe
            Log.e(TAG, "subscribeObservers: $comments")
            commentAdapter.differ.submitList(comments)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Log.e(TAG, "subscribeObservers: ${error?.asString(requireContext())}")
        }
    }

    private fun updateWishImage(isWishListed: Boolean) {
        binding.ivLike.setImageResource(
            if (isWishListed) R.drawable.ic_baseline_favorite
            else R.drawable.ic_baseline_non_favorite
        )
    }

    private fun updateArticleDetail(detail: ArticleDetail) {
        updateImageTitleAndSubtitle(detail)
        updateSectionImages(detail.sections)

    }

    private fun updateImageTitleAndSubtitle(detail: ArticleDetail) {
        binding.tvTitle.text = detail.sections.firstOrNull()?.title
        binding.tvSubTitle.text = detail.address

        val imageUrl = "${detail.medias.toImageUrl()}?format=portrait"
        binding.ivArticleImage.load(imageUrl)

        if (detail.medias.hasVideo()) {
            binding.ivPlay.show()
        }

        binding.rbRating.rating = detail.rating.toFloat()

    }

    private fun updateSectionImages(sections: List<Section>) {
        Log.e(TAG, "updateSectionImages: ${sections.last().title}")
        Log.e(TAG, "updateSectionImages: ${sections.last().description}")
        sectionAdapter.differ.submitList(sections)
    }

    private fun navigateToAuthActivity() {
        requireActivity().startActivity(
            Intent(requireActivity(), AuthActivity::class.java)
        )
    }
}