package com.entrego.entregouser.storage.realm

import android.content.Context
import com.entrego.entregouser.storage.realm.models.AddressType
import com.entrego.entregouser.storage.realm.models.RealmAddressModel
import io.realm.Realm
import io.realm.RealmConfiguration


object RealmController : RealmContract {


    private val DB_NAME: String = "entrego_user_db"

    private val SCHEMA_VERSION: Long = 2

    fun init(ctx: Context) {
        Realm.init(ctx)

        val realmConfiguration = RealmConfiguration.Builder()
                .name(DB_NAME)
                .schemaVersion(SCHEMA_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(realmConfiguration)

    }

    override fun addFavoritePlace(name: String, address: String) {
        Realm.getDefaultInstance().apply {
            beginTransaction()
            copyToRealmOrUpdate(RealmAddressModel(name, address, AddressType.FAVORITE))
            commitTransaction()
        }

    }

    override fun getFavoritesList(): List<RealmAddressModel> =
            Realm.getDefaultInstance()
                    .where(RealmAddressModel::class.java)
                    .equalTo("type", AddressType.FAVORITE.value)
                    .findAll()
                    .toList()


    override fun removeFavorite(address: String) {

        Realm.getDefaultInstance().apply {
            beginTransaction()
            where(RealmAddressModel::class.java)
                    .equalTo("type", AddressType.FAVORITE.value)
                    ?.equalTo("address", address)
                    ?.findFirst()
                    ?.deleteFromRealm()
            commitTransaction()
        }
    }

    override fun clearForNewUser() {
        Realm.getDefaultInstance().apply {
            beginTransaction()
            deleteAll()
            commitTransaction()
        }
    }

    override fun addRecentSearch(address: String) {
        val item = RealmAddressModel(address)
        item.type = AddressType.RECENT_SEARCH.value
        if (isRecentAddressesLimit())
            cleanRecentAddressByLimit()

        Realm.getDefaultInstance().apply {
            beginTransaction()
            copyToRealmOrUpdate(item)
            commitTransaction()
        }
    }

    override fun getRecentSearch(): List<RealmAddressModel> =
            Realm.getDefaultInstance()
                    .where(RealmAddressModel::class.java)
                    .equalTo("type", AddressType.RECENT_SEARCH.value)
                    .findAll()
                    .toList()


    override fun removeAddress(item: RealmAddressModel) {
        Realm.getDefaultInstance().apply {
            beginTransaction()
            item.deleteFromRealm()
            commitTransaction()
        }
    }

    override fun isRecentAddressesLimit(): Boolean {

        Realm.getDefaultInstance().apply {
            val addressCount = where(RealmAddressModel::class.java)
                    .equalTo("type", AddressType.RECENT_SEARCH.value)
                    .findAll()
                    .count()
            return (addressCount > RealmContract.AddressesTableContract.MAX_ROWS)
        }
        return false
    }

    override fun cleanRecentAddressByLimit() {
        Realm.getDefaultInstance().apply {
            val recentAddressList = where(RealmAddressModel::class.java)
                    .equalTo("type", AddressType.RECENT_SEARCH.value)
                    .findAll()
                    .toList()
                    .sortedBy { it.createdTime }
                    .reversed()
            val lastIndex = recentAddressList.lastIndex

            val trashList = recentAddressList
                    .subList(RealmContract.AddressesTableContract.MAX_ROWS, lastIndex)

            beginTransaction()
            trashList.forEach { it.deleteFromRealm() }
            commitTransaction()

        }
    }
}

