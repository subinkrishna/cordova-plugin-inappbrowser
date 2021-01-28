@file:JvmName("InAppBrowserViewUtil")
package org.apache.cordova.inappbrowser

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import org.apache.cordova.LOG

private const val TAG = "InAppBrowser"

const val LOADER_DEFAULT = "default"
const val LOADER_NONE = "none"
const val LOADER_ENTERPRISE = "enterprise"

fun loadingIndicator(
    context: Context,
    type: String? = LOADER_DEFAULT
): View? {
    LOG.d(TAG, "Create loader for: $type")
    return when(type) {
        LOADER_DEFAULT -> defaultLoader(context)
        LOADER_ENTERPRISE -> enterpriseLoader(context)
        LOADER_NONE -> null
        else -> null
    }
}

private fun defaultLoader(context: Context): View {
    val width = 32.toPx(context)
    return ProgressBar(context).apply {
        isIndeterminate = true
        indeterminateTintList = ColorStateList.valueOf(Color.LTGRAY)
        layoutParams = RelativeLayout.LayoutParams(width, width).apply {
            addRule(RelativeLayout.CENTER_IN_PARENT)
        }
    }
}

private fun enterpriseLoader(context: Context): ImageView? {
    val resourceId = context.resources.getIdentifier(
        "ic_gif_enterprise",
        "raw",
        context.packageName
    )

    if (resourceId <= 0) {
        LOG.e(TAG, "No loader resource found")
        return null
    }

    val width = 48.toPx(context)
    val imageView = ImageView(context).apply {
        scaleType = ImageView.ScaleType.FIT_CENTER
        layoutParams = RelativeLayout.LayoutParams(width, width).apply {
            addRule(RelativeLayout.CENTER_IN_PARENT)
        }
    }

    Glide.with(context.applicationContext)
        .load(resourceId)
        .into(imageView)

    return imageView
}

private fun Int.toPx(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}