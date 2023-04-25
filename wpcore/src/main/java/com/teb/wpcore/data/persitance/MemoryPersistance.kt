package com.teb.wpcore.data.persitance

import java.util.HashSet

class MemoryPersistance : Persistance {
    var pageCount : Int = 0

    val slugSet : MutableSet<String> = HashSet()

    override fun incrementPageViewCount() {
        pageCount++
    }

    override fun getPageViewCount(): Int {
        return pageCount
    }

    override fun addToFavoritePostsList(slug: String) {
        slugSet.add(slug)
    }

    override fun getCommaSeperatedSlugsForFavoritePostsList(): String {

       return slugSet.joinToString(separator = ",")
    }

    override fun setCustomLogo(url: String) {
        TODO("Not yet implemented")
    }

    override fun getCustomLogo(): String? {
        TODO("Not yet implemented")
    }
}