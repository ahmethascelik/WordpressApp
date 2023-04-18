package com.teb.wpcore.data.persitance

import android.content.Context

interface Persistance {

    fun incrementPageViewCount(context: Context)

    fun getPageViewCount(context: Context) : Int
}