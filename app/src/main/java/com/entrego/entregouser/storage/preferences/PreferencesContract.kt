package com.entrego.entregouser.storage.preferences

import com.entrego.entregouser.entity.common.PaymentMethodEntity
import com.entrego.entregouser.storage.realm.models.CustomerProfileModel


interface PreferencesContract {
    fun saveHomeAddress(address: String)
    fun getHomeAddressOrEmpty(): String
    fun saveWorkAddress(address: String)
    fun getWorkAddressOrEmpty(): String
    fun clearForNewUser()
    fun setLastEmail(email: String)
    fun getLastEmail(): String
    fun saveProfile(model: CustomerProfileModel)
    fun getProfile(): CustomerProfileModel?
    fun saveDefaultPaymentMethod(method: PaymentMethodEntity): Boolean
    fun getDefaultPaymentMethod(): PaymentMethodEntity
}