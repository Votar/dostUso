package com.entrego.entregouser.storage.preferences

import android.content.Context
import android.content.SharedPreferences
import com.entrego.entregouser.access.EntregoToken

object PreferencesManager {
    val STORAGE_NAME = "ENTREGO_STORE"
    private val KEY_TOKEN = "pref_k_token"

    lateinit private var mSharedPref: SharedPreferences
    fun init(context: Context) {
        mSharedPref = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    }

    fun setToken(value: String) {
        mSharedPref.edit().putString(KEY_TOKEN, value).apply()
    }

    fun getTokenOrEmpty(): String = mSharedPref.getString(KEY_TOKEN, "")

}