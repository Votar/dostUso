package com.entrego.entregouser.storage

import android.content.Context
import com.entrego.entregouser.storage.preferences.PreferencesManager
import com.entrego.entregouser.storage.realm.RealmContract
import com.entrego.entregouser.storage.realm.RealmController
import com.entrego.entregouser.storage.realm.StorageContract
import com.entrego.entregouser.storage.realm.models.AddressType
import com.entrego.entregouser.storage.realm.models.RealmAddressModel
import io.realm.Realm

object EntregoStorage : StorageContract {


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
}