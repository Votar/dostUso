package com.entrego.entregouser.ui.intro.registration.presenter

interface IRegistrationFbPresenter {
    fun requestRegistration(email: String,
                            name: String,
                            token:String,
                            phoneCode: String,
                            phoneNumber: String)
}