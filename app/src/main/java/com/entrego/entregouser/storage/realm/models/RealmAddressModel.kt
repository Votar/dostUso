package com.entrego.entregouser.storage.realm.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*


open class RealmAddressModel() : RealmObject() {
    companion object {
        private var idGenerator: Int = 0
    }

    @PrimaryKey
    var id: Int
    var name: String = ""
    var address: String = ""
    var createdTime = Calendar.getInstance().time.time
    var type: Int = AddressType.OTHER.value

    init {
        id = idGenerator++
    }


    constructor(address: String) : this() {
        this.address = address
    }

    constructor(name: String, address: String) : this(address) {
        this.name = name
    }

    constructor(name: String, address: String, type: AddressType) : this(name, address) {
        this.type = type.value
    }
}