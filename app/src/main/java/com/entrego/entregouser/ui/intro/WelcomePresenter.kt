package com.entrego.entregouser.ui.intro

import android.content.Intent
import com.entrego.entregouser.entity.EntregoPhoneModel
import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.facebook.AccessToken
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult


class WelcomePresenter : BaseMvpPresenter<WelcomeContract.View>(),
        WelcomeContract.Presenter {



    val mCallbackManager = com.facebook.CallbackManager.Factory.create()

    val mLoginFbListener = object : FacebookCallback<LoginResult> {
        override fun onSuccess(loginResult: LoginResult) {

        }

        override fun onCancel() {

        }

        override fun onError(e: FacebookException) {

        }
    }

    override fun attachView(view: WelcomeContract.View) {
        super.attachView(view)
        mView?.registerFacebookCallback(mCallbackManager, mLoginFbListener)
    }

    override fun performActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun loginWithFb() {
        val token = AccessToken.getCurrentAccessToken()
        if (token == null)
            mView?.performFbClick()
        else {

        }
    }

    override fun performFbWithUserData(email: String, phone: EntregoPhoneModel) {

    }
}
