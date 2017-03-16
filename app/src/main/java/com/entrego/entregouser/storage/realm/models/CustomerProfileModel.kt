package com.entrego.entregouser.storage.realm.models

import com.entrego.entregouser.entity.EntregoPhoneModel

open class CustomerProfileModel(val id: Long,
                                var email: String,
                                var phone: EntregoPhoneModel,
                                var name: String)