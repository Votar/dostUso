package com.entrego.entregouser.ui.registration.presenter

interface IRegistrationPresenter {
    fun requestRegistration(email: String,
                            name: String,
                            password: String, confPassword: String,
                            phoneCode: String,
                            phoneNumber: String)
}