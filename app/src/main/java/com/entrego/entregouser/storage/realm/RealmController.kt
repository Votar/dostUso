package com.entrego.entregouser.storage.realm

import android.content.Context
import com.entrego.entregouser.storage.realm.models.RealmAddressModel
import com.entrego.entregouser.storage.realm.StorageContract
import io.realm.Realm
import io.realm.RealmConfiguration


object RealmController : RealmContract {


    private val DB_NAME: String = "entrego_user_db"

    private val SCHEMA_VERSION: Long = 1

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
            copyToRealmOrUpdate(RealmAddressModel(name, address))
            commitTransaction()
        }

    }

    override fun getFavoritesList(): List<RealmAddressModel> =
            Realm.getDefaultInstance()
                    .where(RealmAddressModel::class.java)
                    .findAll()
                    .toList()


    override fun removeFavorite(address: String) {

        Realm.getDefaultInstance().apply {
            beginTransaction()
            where(RealmAddressModel::class.java)
                    .equalTo("address", address)
                    .findFirst()
                    .deleteFromRealm()
            commitTransaction()
        }
    }

}