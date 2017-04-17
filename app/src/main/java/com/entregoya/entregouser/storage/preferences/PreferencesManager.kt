package com.entregoya.entregouser.storage.preferences

import android.content.Context
import android.content.SharedPreferences
import com.entregoya.entregouser.entity.common.PaymentMethodEntity
import com.entregoya.entregouser.entity.common.PaymentMethodType
import com.entregoya.entregouser.storage.realm.models.CustomerProfileModel
import com.entregoya.entregouser.util.GsonHolder

object PreferencesManager : PreferencesContract {
    override fun clearForNewUser() {
        mSharedPref.edit().clear().commit()
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
    private val KEY_DEFAULT_PAYMENT_METHOD = "pref_k_default_payment"
    lateinit private var mSharedPref: SharedPreferences

    fun init(context: Context) {
        mSharedPref = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    }

    fun setToken(value: String) {
        mSharedPref.edit().putString(KEY_TOKEN, value).commit()
    }

    fun getTokenOrEmpty(): String = mSharedPref.getString(KEY_TOKEN, "")

    override fun setLastEmail(email: String) {
        mSharedPref.edit().putString(KEY_LAST_EMAIL, email).apply()
    }

    override fun getLastEmail(): String = mSharedPref.getString(KEY_LAST_EMAIL, "")

    override fun saveProfile(model: CustomerProfileModel) {
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

    override fun saveDefaultPaymentMethod(method: PaymentMethodEntity): Boolean {
        val json = GsonHolder.instance.toJson(method, PaymentMethodEntity::class.java)
        return mSharedPref.edit().putString(KEY_DEFAULT_PAYMENT_METHOD, json).commit()
    }

    override fun getDefaultPaymentMethod(): PaymentMethodEntity {
        val json = mSharedPref.getString(KEY_DEFAULT_PAYMENT_METHOD, "")
        if (json.isNullOrEmpty())
            return PaymentMethodEntity(PaymentMethodType.CASH)
        else
            return GsonHolder.instance
                    .fromJson(json, PaymentMethodEntity::class.java)
    }

}