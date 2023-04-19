package com.teb.wpcore.data.persitance

import android.content.Context

interface Persistance {

    fun incrementPageViewCount(context: Context)

    fun getPageViewCount(context: Context) : Int


    fun addToFavoritePostsList(context: Context, slug: String)

    fun getCommaSeperatedSlugsForFavoritePostsList(context: Context) : String

    fun setCustomLogo(context: Context, url: String)

    fun getCustomLogo(context: Context): String?

}