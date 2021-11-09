package com.example.apteki.utils

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.apteki.R

fun Fragment.toDpi(px: Int): Int {
    return ((requireContext().resources.displayMetrics.density * px) + 0.5f).toInt()
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