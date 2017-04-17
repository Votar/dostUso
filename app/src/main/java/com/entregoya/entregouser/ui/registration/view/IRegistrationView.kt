package com.entregoya.entregouser.ui.registration.view


interface IRegistrationView {
    fun setErrorName(message:String)
    fun setErrorEmail(message: String)
    fun setErrorPassword(message: Int)
    fun setErrorPassword(message: String)
    fun setErrorPhoneCode(message:String)
    fun setErrorPhoneNumber(message:String)
    fun setErrorConfPassword()
    fun setErrorEmailRegistered()
    fun setErrorPhoneRegistered()
    fun showMessage(message:String?)
    fun showProgress()
    fun hideProgress()
    fun successRegistration()

}