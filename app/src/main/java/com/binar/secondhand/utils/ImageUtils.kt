package com.binar.secondhand.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(imageSource : String?) {
    Glide.with(context)
        .load(imageSource)
        .into(this)
}