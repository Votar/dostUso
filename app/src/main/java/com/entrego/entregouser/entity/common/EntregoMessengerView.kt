package com.entrego.entregouser.entity.common

import com.entrego.entregouser.entity.EntregoPhoneModel


class EntregoMessengerView(val email: String,
                           val phone: EntregoPhoneModel,
                           val name: String,
                           val id: Int) {
}