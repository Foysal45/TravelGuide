package com.btb.explorebangladesh.coil

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import com.btb.explorebangladesh.extensions.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.imageview.ShapeableImageView


fun ImageView.load(url: String?, transformation: Transformation = RoundedCornersTransformation()) {
    if (this is ShapeableImageView) {
        setBackgroundColor(context.getColor(R.color.colorGrey))
    }
    load(url) {
        allowHardware(false)
        bitmapConfig(Bitmap.Config.ARGB_8888)
        val shimmer = Shimmer.ColorHighlightBuilder()
            .setBaseColor(ContextCompat.getColor(context, R.color.purple_200))
            .setHighlightColor(ContextCompat.getColor(context, R.color.purple_500))
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(0.7f)
            .setDuration(1800)
            .build()
        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }
        placeholder(shimmerDrawable)

        transformations(transformation)
    }
}

fun ImageView.load(url: String?) {
    if (this is ShapeableImageView) {
        setBackgroundColor(context.getColor(R.color.colorGrey))
    }
    load(url) {
        allowHardware(false)
        bitmapConfig(Bitmap.Config.ARGB_8888)
        val shimmer = Shimmer.ColorHighlightBuilder()
            .setBaseColor(ContextCompat.getColor(context, R.color.purple_200))
            .setHighlightColor(ContextCompat.getColor(context, R.color.purple_500))
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(0.7f)
            .setDuration(1800)
            .build()
        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }
        placeholder(shimmerDrawable)
    }
}

fun ImageView.load(
    @DrawableRes drawableId: Int,
    transformation: Transformation = RoundedCornersTransformation()
) {
    load(drawableId) {
        allowHardware(false)
        bitmapConfig(Bitmap.Config.ARGB_8888)
        transformations(transformation)
    }
}


fun ImageView.load(
    @DrawableRes drawableId: Int
) {
    load(drawableId) {
        crossfade(true)
        allowHardware(false)
        bitmapConfig(Bitmap.Config.ARGB_8888)
    }
}

