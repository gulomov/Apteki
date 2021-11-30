package com.example.apteki.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.app.Dialog
import android.content.Context
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
import android.util.Log
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Context.format(text: TextView) {

    val simpleText = text.text.toString()
    val longval = simpleText.toLong()
    val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
    formatter.applyPattern("#,###,###,###")
    return text.setText(formatter.format(longval))
}


fun Fragment.toDpi(px: Int): Int {
    return ((requireContext().resources.displayMetrics.density * px) + 0.5f).toInt()
}

fun Fragment.dialogForCalendar(text: String, textView: TextView) {
    val dialog = Dialog(requireContext(), R.style.MyAlertDialogTheme)
    dialog.setCancelable(true)
    dialog.setContentView(R.layout.date_picker)
    dialog.show()

    var date = dialog.findViewById<DatePicker>(R.id.date)
    val today = Calendar.getInstance()
    date.init(
        today.get(Calendar.YEAR),
        today.get(Calendar.MONTH),
        today.get(Calendar.DAY_OF_MONTH)
    ) { view, year, monthOfYear, dayOfMonth ->
        val month = monthOfYear + 1
        val msg = "$year-$month-$dayOfMonth"
        if (text == "fromData") {
            textView.text = msg
        } else if (text == "toData") {
            textView.text = msg
        }

        dialog.dismiss()
    }

}

fun Activity.hideSoftKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(
            Context
                .INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}

fun Fragment.hideKeyboard() {
    activity?.hideSoftKeyboard()
}

fun Fragment.optionDone(appCompatEditText: AppCompatEditText) {
    appCompatEditText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            hideKeyboard()
            return@OnEditorActionListener true
        }
        false
    })
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

fun View.hide(): View {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
        Log.d("DefaultTag", "actually hidden")
    }
    return this
}

fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun Fragment.navigate(resId: Int, args: Bundle? = null) {

    val builder = NavOptions.Builder()
        .setEnterAnim(R.anim.enter)
        .setExitAnim(R.anim.exit)
        .setPopEnterAnim(R.anim.pop_enter)
        .setPopExitAnim(R.anim.pop_exit)
    hideKeyboard()
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
//                if (itemposition == 0)
//                    outRect.top = space
//                else {
//                    outRect.top = 0
//                }
            }
        } else {
            outRect.top = 2
            outRect.bottom = space
            outRect.right = space
            outRect.left = space
        }

    }

}
