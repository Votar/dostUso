package com.entregoya.entregouser.storage.realm.models

import com.entregoya.entregouser.entity.EntregoPhoneModel
import com.entregoya.entregouser.entity.common.EntregoPriceEntity

open class CustomerProfileModel(var email: String?,
                                var name: String?,
                                var phone: EntregoPhoneModel?,
                                var balance: EntregoPriceEntity? = null,
                                val id: Long = 0)