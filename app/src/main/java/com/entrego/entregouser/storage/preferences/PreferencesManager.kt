package com.entrego.entregouser.storage.preferences

import android.content.Context
import android.content.SharedPreferences
import com.entrego.entregouser.storage.realm.models.CustomerProfileModel
import com.entrego.entregouser.util.GsonHolder

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
    private val KEY_LAST_EMAIL = "pref_k_last_email"
    private val KEY_CUSTOMER_PROFILE = "pref_k_profile"

    lateinit private var mSharedPref: SharedPreferences
    fun init(context: Context) {
        mSharedPref = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    }

    fun setToken(value: String) {
        mSharedPref.edit().putString(KEY_TOKEN, value).apply()
    }

    fun getTokenOrEmpty(): String = mSharedPref.getString(KEY_TOKEN, "")

    override fun setLastEmail(email: String) {
        mSharedPref.edit().putString(KEY_LAST_EMAIL, email).apply()
    }

    override fun getLastEmail(): String = mSharedPref.getString(KEY_LAST_EMAIL, "")

    override fun saveProfileJson(model: CustomerProfileModel) {
        val serialize = GsonHolder.instance
                .toJson(model, CustomerProfileModel::class.java)
        mSharedPref
                .edit()
                .putString(KEY_CUSTOMER_PROFILE, serialize)
                .apply()
    }

    override fun getProfile(): CustomerProfileModel? {

        val profileJson = mSharedPref.getString(KEY_CUSTOMER_PROFILE, "")

        if (profileJson.isNotEmpty())
            return GsonHolder
                    .instance
                    .fromJson(profileJson, CustomerProfileModel::class.java)
        else return null

    }

}