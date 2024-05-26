package com.bkalysh.devicer.utils

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun setRoundImageView(activity: Activity, view: ImageView, resourceID: Int) {
    Glide.with(activity)
        .load(resourceID)
        .apply(RequestOptions.circleCropTransform())
        .into(view)
}