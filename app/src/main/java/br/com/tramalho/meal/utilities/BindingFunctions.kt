package br.com.tramalho.meal.utilities

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import br.com.tramalho.meal.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


@BindingAdapter(value = arrayOf("imageUrl", "placeholder"), requireAll = false)
fun loadImage(imageView: ImageView, url: String?, placeHolder: Int = R.drawable.placeholder) {
    val context = imageView.context

    when (url.isNullOrBlank()) {
        true -> imageView.setImageResource(placeHolder)
        else -> {
            Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }
    }
}

@BindingAdapter("android:src")
fun setSrcVector(view: ImageView, @DrawableRes drawable: Int) {
    view.setImageResource(drawable)
}