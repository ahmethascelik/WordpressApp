package com.teb.wpcore.data.persitance

import android.content.Context
import android.preference.PreferenceManager
import java.util.HashSet

class StoragePersistance(val context: Context) : Persistance {

    private val KEY_PAGE_COUNT: String = "PAGE_COUNT"

    private val KEY_CUSTOM_LOGO: String = "CUSTOM_LOGO"

    private val KEY_FAVORITE: String = "KEY_FAVORITE"

    override fun incrementPageViewCount() {
        val currentVal = getPageViewCount()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        val editor = sharedPreferences.edit()

        editor.putInt(KEY_PAGE_COUNT, currentVal + 1 )

        editor.commit()

    }

    override fun getPageViewCount(): Int {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getInt(KEY_PAGE_COUNT, 0)
    }



    override fun setCustomLogo(url: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        val editor = sharedPreferences.edit()
        editor.putString(KEY_CUSTOM_LOGO, url)
        editor.commit()
    }

    override fun getCustomLogo(): String? {

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(KEY_CUSTOM_LOGO, null)
    }


    override fun addToFavoritePostsList( slug: String) {

        val currentFavorites = getCommaSeperatedSlugsForFavoritePostsList()

        val mergedFavorites = "$currentFavorites,$slug"

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        val editor = sharedPreferences.edit()

        editor.putString(KEY_FAVORITE, mergedFavorites)

        editor.commit()
    }

    override fun getCommaSeperatedSlugsForFavoritePostsList(): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);


        return sharedPreferences.getString(KEY_FAVORITE, "").toString()
    }
}