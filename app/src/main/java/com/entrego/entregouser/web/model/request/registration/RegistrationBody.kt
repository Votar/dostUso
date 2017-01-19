package com.entrego.entregouser.web.model.request.registration

import com.entrego.entregouser.entity.EntregoPhoneModel

/**
 * Created by bertalt on 29.11.16.
 */
class RegistrationBody(val email: String,
                       val name: String,
                       val password: String,
                       val phone: EntregoPhoneModel) {
}