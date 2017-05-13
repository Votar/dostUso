package com.entregoya.entregouser.storage.preferences

import com.entregoya.entregouser.entity.common.PaymentMethodEntity
import com.entregoya.entregouser.storage.realm.models.CustomerProfileModel
import com.entregoya.entregouser.web.model.response.card.EntregoCreditCardEntity


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
    fun getCardList(): List<EntregoCreditCardEntity>
    fun saveCardList(cardList: List<EntregoCreditCardEntity>)
}