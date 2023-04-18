package com.teb.wpcore.data.persitance

import android.content.Context
import java.util.HashSet

class MemoryPersistance : Persistance {
    var pageCount : Int = 0

    val slugSet : MutableSet<String> = HashSet()

    override fun incrementPageViewCount(context: Context) {
        pageCount++
    }

    override fun getPageViewCount(context: Context): Int {
        return pageCount
    }

    override fun addToFavoritePostsList(context: Context, slug: String) {
        slugSet.add(slug)
    }

    override fun getCommaSeperatedSlugsForFavoritePostsList(context: Context): String {

       return slugSet.joinToString(separator = ",")
    }
}