package com.entrego.entregouser.storage.realm

import com.entrego.entregouser.storage.realm.models.AddressType
import com.entrego.entregouser.storage.realm.models.RealmAddressModel

interface RealmContract {
    fun addFavoritePlace(name:String, address: String)
    fun getFavoritesList(): List<RealmAddressModel>
    fun removeFavorite(address: String)
    fun removeAddress(item:RealmAddressModel)
    fun clearForNewUser()
    fun addRecentSearch(address:String)
    fun getRecentSearch():List<RealmAddressModel>

}