package com.example.googlemapsexamples.utill

import android.app.Activity
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.androidadvance.topsnackbar.TSnackbar
import com.example.googlemapsexamples.R
import kotlin.math.abs

/**
 * Created by TharunKumarC on 13, October, 2020
 * Copyrights (c) 2020.
 */

fun showSnackBar(message: String, context: Activity?, @ColorRes bgColor: Int) {
    var Y1 = 0.0f
    var Y2 = 0.0f
    val MIN_DISTANCE = 50
    val snackBar =
        context?.findViewById<View>(android.R.id.content)
            ?.let { TSnackbar.make(it, message, TSnackbar.LENGTH_LONG) }
    snackBar?.setActionTextColor(Color.WHITE)
    val snackBarView = snackBar?.view
    snackBarView?.setBackgroundColor(
        ContextCompat.getColor(
            context,
            bgColor
        )
    )
    snackBar?.setMaxWidth(Int.MAX_VALUE)
    val textView =
        snackBarView?.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text) as? TextView
    textView?.setTextColor(Color.WHITE)
    textView?.textAlignment = View.TEXT_ALIGNMENT_CENTER
    textView?.setTextSize(
        TypedValue.COMPLEX_UNIT_PX,
        context.resources?.getDimension(R.dimen.sp13) ?: 12.0f
    )

    val myLayout = snackBar?.view
    myLayout?.setOnTouchListener { p0, event ->
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                Y1 = event.y
            }
            MotionEvent.ACTION_UP -> {
                Y2 = event.y
                val deltaX = Y2 - Y1
                if (abs(deltaX) > MIN_DISTANCE) {
                    if (Y2 > Y1) {
                        // top to bottom swipe action
                    } else {
                        // bottom to top swipe action
                        snackBar.dismiss()
                    }
                }
            }


        }
        false
    }
    textView?.gravity = Gravity.CENTER
    textView?.maxLines = 3
    context?.let {
        val mainColorPrimaryDark = it.theme?.resources?.getColor(R.color.colorPrimaryDark) ?: ContextCompat.getColor(
                it,
                R.color.colorPrimaryDark
            )
        changeStatusBarColor(it, ContextCompat.getColor(it, bgColor))
        snackBar?.setCallback(object : TSnackbar.Callback() {
            override fun onShown(snackbar: TSnackbar?) {
                super.onShown(snackbar)
                changeStatusBarColor(it, ContextCompat.getColor(it, bgColor))
            }

            override fun onDismissed(snackbar: TSnackbar?, event: Int) {
                super.onDismissed(snackbar, event)
                when (event) {
                    DISMISS_EVENT_CONSECUTIVE -> {
                        return
                    }
                    else -> {
                        changeStatusBarColor(it, mainColorPrimaryDark)
                    }
                }
            }
        })
    }
    snackBar?.show()
}

private fun changeStatusBarColor(activity: Activity?, color: Int) {
    activity?.let { activity.window?.statusBarColor = color }
}
