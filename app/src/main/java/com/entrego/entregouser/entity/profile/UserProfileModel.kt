package com.entrego.entregouser.entity.profile

import com.entrego.entregouser.entity.EntregoPhoneModel

class UserProfileModel(val email: String?,
                       val name: String?,
                       val phone: EntregoPhoneModel?) {
    override fun toString(): String {
        return "UserProfileModel(email='$email', name='$name', phone=$phone)"
    }


    class Builder {
        private var email: String? = null
        private var name: String? = null
        private var phone: EntregoPhoneModel? = null
        fun setName(name: String) {
            this.name
        }

        fun setEmail(email: String) {
            this.email = email
        }

        fun setPhone(phone: EntregoPhoneModel) {
            this.phone = phone
        }

        fun build(): UserProfileModel = UserProfileModel(
                email, name, phone
        )
    }
}