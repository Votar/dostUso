package com.entregoya.entregouser.ui.intro

import android.content.Intent
import com.entregoya.entregouser.entity.EntregoPhoneModel
import com.entregoya.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entregoya.entregouser.mvp.view.IBaseMvpView
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.login.LoginResult


interface WelcomeContract {
    interface View : IBaseMvpView {
        fun registerFacebookCallback(manager: CallbackManager, callback: FacebookCallback<LoginResult>)
        fun showRootView()
        fun performFbClick()
        fun showEmailPhoneInput()
        fun showRegistrationWithFb(fbToken: String)
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun performActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun loginWithFb()
        fun performFbWithUserData(email: String, phone: EntregoPhoneModel)
    }
}