package com.entrego.entregouser.storage.preferences

import android.content.Context
import android.content.SharedPreferences
import com.entrego.entregouser.storage.EntregoStorage

/**
 * Created by Admin on 19.01.2017.
 */
object PreferencesManager{
    val STORAGE_NAME = "ENTREGO_STORE"
    private val KEY_TOKEN = "pref_k_token"

    lateinit private var mSharedPref: SharedPreferences
    fun init(context: Context) {
        mSharedPref = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    }

    fun getTokenOrEmpty(): String = mSharedPref.getString(KEY_TOKEN, "")
    fun setToken(value: String) {
        mSharedPref.edit().putString(KEY_TOKEN, value).apply()
    }
}