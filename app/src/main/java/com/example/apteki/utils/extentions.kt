package com.example.apteki.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.apteki.R
import android.text.method.PasswordTransformationMethod
import android.view.ViewGroup
import android.view.animation.TranslateAnimation


fun Fragment.toDpi(px: Int): Int {
    return ((requireContext().resources.displayMetrics.density * px) + 0.5f).toInt()
}


fun Fragment.slideUp(view: View) {
    view.visibility = View.VISIBLE
    val animate = TranslateAnimation(
        0F,  // fromXDelta
        0F,  // toXDelta
        view.height.toFloat(),  // fromYDelta
        0F
    ) // toYDelta
    animate.duration = 500
    animate.fillAfter = true
    view.startAnimation(animate)
}

fun Fragment.slideDown(view: View) {
    val animate = TranslateAnimation(
        0F,  // fromXDelta
        0F,  // toXDelta
        0F,  // fromYDelta
        view.height.toFloat()
    ) // toYDelta
    animate.duration = 500
    animate.fillAfter = true
    view.startAnimation(animate)
}


fun Fragment.animUp(view: View) {
    view.visibility = View.VISIBLE
    val layoutParams = view.layoutParams
    layoutParams.height = 1
    view.layoutParams = layoutParams
    view.measure(
        View.MeasureSpec.makeMeasureSpec(
            Resources.getSystem().displayMetrics.widthPixels,
            View.MeasureSpec.EXACTLY
        ),
        View.MeasureSpec.makeMeasureSpec(
            0,
            View.MeasureSpec.UNSPECIFIED
        )
    )
    val height = view.measuredHeight
    val valueAnimator: ValueAnimator = ObjectAnimator.ofInt(1, height)
    valueAnimator.addUpdateListener { animation ->
        val value = animation.animatedValue as Int
        if (height > value) {
            val layoutParams = view.layoutParams
            layoutParams.height = value
            view.layoutParams = layoutParams
        } else {
            val layoutParams = view.layoutParams
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            view.layoutParams = layoutParams
        }
    }
    valueAnimator.start()
}

fun Fragment.animDown(view: View) {
    view.post {
        val height = view.height
        val valueAnimator = ObjectAnimator.ofInt(height, 0)
        valueAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            if (value > 0) {
                val layoutParams = view.layoutParams
                layoutParams.height = value
                view.layoutParams = layoutParams
            } else {
                view.visibility = View.GONE
            }
        }
        valueAnimator.start()
    }
}

fun Fragment.navigate(resId: Int, args: Bundle? = null) {

    val builder = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in)
        .setExitAnim(R.anim.slide_out)
        .setPopEnterAnim(R.anim.fade_in)
        .setPopExitAnim(R.anim.fade_out)

    this.findNavController().navigate(resId, args, builder.build())
}

class SpacesItemDecoration(
    private val space: Int,
    private val vertical: Boolean = true,
    private val span: Int = 2
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (vertical) {
            val itemposition = parent.getChildLayoutPosition(view)
            if (span > 1) {
                val column = itemposition % span

                outRect.left = space - column * space / span
                outRect.right = (column + 1) * space / span
                outRect.bottom = space

                if (itemposition < span) {
                    outRect.top = space
                } else {
                    outRect.top = 0
                }
            } else {
                outRect.left = space
                outRect.right = space
                outRect.bottom = space - space / 4
                outRect.top = 0
                if (itemposition == 0)
                    outRect.top = space
                else {
                    outRect.top = 0
                }
            }
        } else {
            outRect.top = 2
            outRect.bottom = space
            outRect.right = space
            outRect.left = space
        }

    }
}
