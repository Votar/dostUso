package com.entregoya.entregouser.storage

import android.content.Context
import com.entregoya.entregouser.entity.common.PaymentMethodEntity
import com.entregoya.entregouser.storage.preferences.PreferencesManager
import com.entregoya.entregouser.storage.realm.RealmController
import com.entregoya.entregouser.storage.realm.models.CustomerProfileModel
import com.entregoya.entregouser.storage.realm.models.RealmAddressModel
import com.entregoya.entregouser.web.model.response.card.EntregoCreditCardEntity

object EntregoStorage : StorageContract {
    override fun saveCardList(cardList: List<EntregoCreditCardEntity>) {
        PreferencesManager.saveCardList(cardList)
    }

    override fun getCardList(): List<EntregoCreditCardEntity> = PreferencesManager.getCardList()

    override fun getRecentSearch(): List<RealmAddressModel> = RealmController.getRecentSearch()

    override fun addRecentSearch(address: String) {
        RealmController.addRecentSearch(address)
    }

    override fun saveHomeAddress(address: String) {
        PreferencesManager.saveHomeAddress(address)
    }

    override fun getHomeAddressOrEmpty(): String = PreferencesManager.getHomeAddressOrEmpty()

    override fun saveWorkAddress(address: String) {
        PreferencesManager.saveWorkAddress(address)
    }

    override fun getWorkAddressOrEmpty(): String = PreferencesManager.getWorkAddressOrEmpty()

    override fun addFavoritePlace(name: String, address: String) {
        RealmController.addFavoritePlace(name, address)
    }

    override fun getFavoritesList(): List<RealmAddressModel> = RealmController.getFavoritesList()

    override fun removeFavorite(address: String) {
        RealmController.removeFavorite(address)
    }

    fun init(context: Context) {
        PreferencesManager.init(context)
        RealmController.init(context)
    }

    fun getTokenOrEmpty(): String = PreferencesManager.getTokenOrEmpty()

    override fun clear() {
        RealmController.clearForNewUser()
        PreferencesManager.clearForNewUser()
    }

    override fun removeAddress(item: RealmAddressModel) {
        RealmController.removeAddress(item)
    }

    override fun getLastEmail(): String = PreferencesManager.getLastEmail()

    override fun setLastEmail(email: String) {
        PreferencesManager.setLastEmail(email)
    }

    override fun saveProfile(model: CustomerProfileModel) {
        PreferencesManager.saveProfile(model)
    }

    override fun getProfile(): CustomerProfileModel? = PreferencesManager.getProfile()

    override fun setDefaultPaymentMethod(method: PaymentMethodEntity): Boolean
            = PreferencesManager.saveDefaultPaymentMethod(method)

    override fun getDefaultPaymentMethod(): PaymentMethodEntity =
            PreferencesManager.getDefaultPaymentMethod()
}
