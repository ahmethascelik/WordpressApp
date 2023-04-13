package com.teb.wpcore.ui.util

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url :String?){
    if (url != null && url.isNotEmpty()) {
        Picasso.get().load(url).into(this)
    }
}