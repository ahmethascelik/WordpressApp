package com.teb.wpcore.data.persitance

interface Persistance {

    fun incrementPageViewCount()

    fun getPageViewCount(): Int


    fun addToFavoritePostsList(slug: String)

    fun getCommaSeperatedSlugsForFavoritePostsList(): String

    fun setCustomLogo(url: String)

    fun getCustomLogo(): String?

}