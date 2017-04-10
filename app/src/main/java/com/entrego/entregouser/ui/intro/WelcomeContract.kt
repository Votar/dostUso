package com.entrego.entregouser.ui.intro

import android.content.Intent
import com.entrego.entregouser.entity.EntregoPhoneModel
import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.login.LoginResult


interface WelcomeContract {
    interface View : IBaseMvpView {
        fun registerFacebookCallback(manager: CallbackManager, callback: FacebookCallback<LoginResult>)
        fun showRootView()
        fun performFbClick()
        fun showEmailPhoneInput()
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun performActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun loginWithFb()
        fun performFbWithUserData(email: String, phone: EntregoPhoneModel)
    }
}