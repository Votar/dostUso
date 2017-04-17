package com.entrego.entregouser.ui.intro.registration.view


interface IRegistrationFbView {
    fun setErrorName(message:String)
    fun setErrorEmail(message: String)
    fun setErrorPhoneCode(message:String)
    fun setErrorPhoneNumber(message:String)
    fun setErrorEmailRegistered()
    fun setErrorPhoneRegistered()
    fun showMessage(message:String?)
    fun showProgress()
    fun hideProgress()
    fun successRegistration()

}