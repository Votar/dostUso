package com.entregoya.entregouser.web.model.request.registration

import com.entregoya.entregouser.entity.EntregoPhoneModel

class RegistrationBody(val email: String,
                       val name: String,
                       val password: String,
                       val phone: EntregoPhoneModel,
                       val type: RegistrationType = RegistrationType.INTERNAL)