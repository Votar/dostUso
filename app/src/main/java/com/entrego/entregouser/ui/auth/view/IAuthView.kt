package com.entrego.entregouser.ui.auth.view

import android.content.Context


interface IAuthView {
    fun getContext() : Context
    fun setErrorEmail(message: String)
    fun setErrorPassword(message: String)
    fun showMessage(message:String?)
    fun showProgress()
    fun hideProgress()
    fun goToRegistration()
    fun goToMainScreen()
    fun successRestorePassword()
}