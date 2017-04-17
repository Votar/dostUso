package com.entrego.entregouser.storage.realm

import com.entrego.entregouser.entity.common.PaymentMethodEntity
import com.entrego.entregouser.storage.realm.models.CustomerProfileModel
import com.entrego.entregouser.storage.realm.models.RealmAddressModel

interface StorageContract {
    fun clear()
    fun saveHomeAddress(address: String)
    fun getHomeAddressOrEmpty(): String
    fun saveWorkAddress(address: String)
    fun getWorkAddressOrEmpty(): String
    fun addFavoritePlace(name: String, address: String)
    fun getFavoritesList(): List<RealmAddressModel>
    fun removeFavorite(address: String)
    fun addRecentSearch(address: String)
    fun getRecentSearch(): List<RealmAddressModel>
    fun removeAddress(item: RealmAddressModel)
    fun setLastEmail(email: String)
    fun getLastEmail(): String
    fun saveProfile(model: CustomerProfileModel)
    fun getProfile(): CustomerProfileModel?
    fun setDefaultPaymentMethod(method: PaymentMethodEntity): Boolean
    fun getDefaultPaymentMethod(): PaymentMethodEntity
}