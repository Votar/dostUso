package com.entregoya.entregouser.storage.realm.models

import io.realm.RealmObject

open class PhoneRealmModel : RealmObject() {
    var code: String = ""
    var number: String = ""
}
