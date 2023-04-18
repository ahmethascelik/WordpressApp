package com.teb.wpcore.data.persitance

import android.content.Context

class MemoryPersistance : Persistance {
    var pageCount : Int = 0

    override fun incrementPageViewCount(context: Context) {
        pageCount++
    }

    override fun getPageViewCount(context: Context): Int {
        return pageCount
    }
}