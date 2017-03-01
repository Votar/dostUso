package com.entrego.entregouser.storage.preferences

import android.content.Context
import android.content.SharedPreferences
import com.entrego.entregouser.access.EntregoToken

object PreferencesManager : PreferencesContract {
    override fun clearForNewUser() {
        mSharedPref.edit().clear().apply()
    }

    override fun getWorkAddressOrEmpty(): String = mSharedPref.getString(KEY_WORK_ADDRESS, "")
    override fun getHomeAddressOrEmpty(): String = mSharedPref.getString(KEY_HOME_ADDRESS, "")
    override fun saveHomeAddress(address: String) {
        mSharedPref.edit().putString(KEY_HOME_ADDRESS, address).apply()
    }
    override fun saveWorkAddress(address: String) {
        mSharedPref.edit().putString(KEY_WORK_ADDRESS, address).apply()
    }

    val STORAGE_NAME = "ENTREGO_STORE"
    private val KEY_TOKEN = "pref_k_token"
    private val KEY_HOME_ADDRESS = "pref_k_home_address"
    private val KEY_WORK_ADDRESS = "pref_k_work_address"

    lateinit private var mSharedPref: SharedPreferences
    fun init(context: Context) {
        mSharedPref = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    }

    fun setToken(value: String) {
        mSharedPref.edit().putString(KEY_TOKEN, value).apply()
    }

    fun getTokenOrEmpty(): String = mSharedPref.getString(KEY_TOKEN, "")

}