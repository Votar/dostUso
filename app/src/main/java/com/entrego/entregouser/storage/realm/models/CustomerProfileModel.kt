package com.entrego.entregouser.storage.realm.models

import com.entrego.entregouser.entity.EntregoPhoneModel

open class CustomerProfileModel(var email: String?,
                                var name: String?,
                                var phone: EntregoPhoneModel?,
                                val id: Long = 0)