package com.entregoya.entregouser.web.model.request.registration

import com.entregoya.entregouser.entity.EntregoPhoneModel

class RegistrationFbBody(val email: String,
                       val name: String,
                       val token: String,
                       val phone: EntregoPhoneModel,
                       val type: RegistrationType = RegistrationType.FACEBOOK) {
}