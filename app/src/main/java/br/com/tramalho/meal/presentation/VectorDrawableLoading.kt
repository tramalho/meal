package br.com.tramalho.meal.presentation

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import br.com.tramalho.data.infraestructure.toAlphaNumeric


class VectorDrawableLoading @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var avd: AnimatedVectorDrawableCompat? = null
    private val attributes = listOf(
        Attributes("http://schemas.android.com/apk/res-auto", "srcCompat"),
        Attributes("http://schemas.android.com/apk/res/android", "background")
    )

    init {
        attrs?.let { param ->
            attributes.forEach {
                val background = param.getAttributeValue(it.namespace, it.property)
                background?.let { config(context, background) }
            }
        }
    }

    private fun config(context: Context, background: String?) {

        background?.let {

            avd = AnimatedVectorDrawableCompat.create(context, it.toAlphaNumeric().toInt())

            animation.apply { setImageDrawable(avd) }

            runAnimation()
        }
    }

    private fun runAnimation() {

        avd?.let {
            val runAvd = {
                it.start()
            }

            it.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    runAvd.invoke()
                }
            })
            runAvd.invoke()
        }
    }

    private fun stopAnimation() {
        avd?.stop()
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)

        when (visibility) {
            View.VISIBLE -> runAnimation()
            View.GONE, View.INVISIBLE -> stopAnimation()
        }
    }

    private data class Attributes(val namespace: String, val property: String)
}