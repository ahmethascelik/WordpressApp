package com.teb.wpcore.data.persitance

import android.content.Context
import android.preference.PreferenceManager

class StoragePersistance : Persistance {
    private val KEY_PAGE_COUNT: String = "PAGE_COUNT"
    private val KEY_CUSTOM_LOGO: String = "CUSTOM_LOGO"

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
        TODO("Not yet implemented")
    }

    override fun getCommaSeperatedSlugsForFavoritePostsList(context: Context): String {
        TODO("Not yet implemented")
    }

    override fun setCustomLogo(context: Context, url: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        val editor = sharedPreferences.edit()
        editor.putString(KEY_CUSTOM_LOGO, url)
        editor.commit()
    }

    override fun getCustomLogo(context: Context): String? {

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(KEY_CUSTOM_LOGO, null)
    }


}