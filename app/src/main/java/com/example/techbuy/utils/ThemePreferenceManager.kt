package com.example.techbuy.utils

import android.content.Context
import android.content.SharedPreferences

object ThemePreferenceManager {

    private const val PREF_NAME = "theme_prefs"
    private const val KEY_IS_DARK_THEME = "is_dark_theme"

    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun saveTheme(isDarkTheme: Boolean?) {
        if (isDarkTheme == null) {
            sharedPreferences?.edit()?.remove(KEY_IS_DARK_THEME)?.apply()
        } else {
            sharedPreferences?.edit()?.putBoolean(KEY_IS_DARK_THEME, isDarkTheme)?.apply()
        }
    }

    // Returns null if no preference is saved, true if dark, false if light.
    fun loadTheme(): Boolean? {
        return if (sharedPreferences?.contains(KEY_IS_DARK_THEME) == true) {
            sharedPreferences?.getBoolean(KEY_IS_DARK_THEME, false)
        } else {
            null // No preference saved yet
        }
    }
}
