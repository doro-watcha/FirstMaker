package com.math.firstMaker.common

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide

@BindingAdapter("android:visibility")
fun View.setVisibility(visible : Boolean) {
    this.visibility = if(visible) View.VISIBLE else View.GONE
}



@BindingAdapter("app:swiperefreshlayout_refreshing")
fun SwipeRefreshLayout.setRefreshingBinding(isRefreshing : Boolean) {
    this.isRefreshing = isRefreshing
}

@BindingAdapter("app:view_music_title_horizontal_flow_anim")
fun View.setHorizontalFlowAnimation(start : Any?) {
    val parentWidth = (this.parent as View).width.toFloat()

    ObjectAnimator.ofFloat(this,"translationX",parentWidth * 0.8f,-parentWidth * 1.2f).apply {

        setAutoCancel(true)
        duration = 30000L

        repeatCount = ObjectAnimator.INFINITE
        repeatMode = ObjectAnimator.RESTART

        interpolator = LinearInterpolator()

        start()
    }
}

@BindingAdapter("android:text_animate_alpha")
fun TextView.setTextWithAlphaAnimation(str : String?) {
    ObjectAnimator.ofFloat(this@setTextWithAlphaAnimation,"alpha",1f,0f).apply {
        repeatCount = 1
        repeatMode = ObjectAnimator.REVERSE

        duration = 250L

        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationRepeat(animation: Animator?) {
                this@setTextWithAlphaAnimation.text = str ?: ""
            }
        })

        start()
    }
}

@BindingAdapter("imageSrc")
fun ImageView.setImageSrcGlide(src : Uri?) {
    Glide.with(this).load(src).into(this)
}
@BindingAdapter("imageSrc")
fun ImageView.setImageSrcGlide(src : String?) {
    Glide.with(this).load(src).into(this)
}

@BindingAdapter("imageSrcBitmap")
fun ImageView.setImageSrcBitmapGlide ( bitmap : Bitmap?) {
    Glide.with(this).load(bitmap).into(this)
}

@BindingAdapter("srcUrl", "placeholder", requireAll = false)
fun ImageView.loadUrlAsync(url: String?, placeholder: Drawable? = null) {
    if (url == null) {
        Glide.with(this).load(placeholder).into(this)
    } else {
        Glide.with(this).load(url)
            .apply {
                if (placeholder != null)
                    (placeholder)
            }
            .into(this)
    }
}