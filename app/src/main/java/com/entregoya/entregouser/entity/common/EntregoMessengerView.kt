package com.entregoya.entregouser.entity.common

import com.entregoya.entregouser.entity.EntregoPhoneModel


class EntregoMessengerView(val email: String,
                           val phone: EntregoPhoneModel,
                           val name: String,
                           val score: Float,
                           val id: Long)