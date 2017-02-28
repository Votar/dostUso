package com.entrego.entregouser.storage.realm.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class RealmAddressModel() : RealmObject() {
    companion object {
        private var idGenerator: Int = 0
    }
    @PrimaryKey
    var id: Int
    var name: String = ""
    var address: String = ""
    init {
        id = idGenerator++
    }


    constructor(address: String) : this() {
        this.address = address
    }

    constructor(name: String, address: String) : this(address) {
        this.name = name
    }
}