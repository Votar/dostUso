package com.entregoya.entregouser.ui.auth.view

import android.content.Context


interface IAuthView {
    fun getContext() : Context
    fun setErrorEmail(message: String)
    fun setErrorPassword(message: String)
    fun showMessage(message:String?)
    fun showProgress()
    fun hideProgress()
    fun goToMainScreen()
    fun successRestorePassword()
}