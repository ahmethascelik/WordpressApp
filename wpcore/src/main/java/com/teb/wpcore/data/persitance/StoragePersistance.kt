package com.teb.wpcore.data.persitance

import android.content.Context
import android.preference.PreferenceManager
import java.util.HashSet

class StoragePersistance : Persistance {
    private val KEY_PAGE_COUNT: String = "PAGE_COUNT"
    private val KEY_FAVORITE: String = "KEY_FAVORITE"


    override fun incrementPageViewCount(context: Context) {
        val currentVal = getPageViewCount(context)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        val editor = sharedPreferences.edit()

        editor.putInt(KEY_PAGE_COUNT, currentVal + 1 )

        editor.commit()

    }

    override fun getPageViewCount(context: Context): Int {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getInt(KEY_PAGE_COUNT, 0)
    }

    override fun addToFavoritePostsList(context: Context, slug: String) {

        val currentFavorites = getCommaSeperatedSlugsForFavoritePostsList(context)

        val mergedFavorites = "$currentFavorites,$slug"

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        val editor = sharedPreferences.edit()

        editor.putString(KEY_FAVORITE, mergedFavorites)

        editor.commit()
    }

    override fun getCommaSeperatedSlugsForFavoritePostsList(context: Context): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(KEY_FAVORITE, "").toString()
    }
}