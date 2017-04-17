package com.entrego.entregouser.ui.intro

import android.content.Intent
import com.entrego.entregouser.entity.EntregoPhoneModel
import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.storage.preferences.PreferencesManager
import com.entrego.entregouser.ui.intro.model.LoginWithFbRequest
import com.entrego.entregouser.web.api.ApiContract
import com.facebook.AccessToken
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult


class WelcomePresenter : BaseMvpPresenter<WelcomeContract.View>(),
        WelcomeContract.Presenter {

    val mCallbackManager = com.facebook.CallbackManager.Factory.create()

    val mLoginFbListener = object : FacebookCallback<LoginResult> {
        override fun onSuccess(loginResult: LoginResult) {
            loginWithFb()
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

            val mResponseListenerLoginFb = object : LoginWithFbRequest.LoginWithFbRequestListener {
                override fun onSuccessLoginWithFbRequest(token: String) {
                    EntregoStorage.clear()
                    PreferencesManager.setToken(token)
                    mView?.showRootView()
                }

                override fun onFailureLoginWithFbRequest(code: Int?, message: String?) {
                    when (code) {
                        ApiContract.RESPONSE_INVALID_TOKEN -> {
                            mView?.showRegistrationWithFb(token.token)
                        }
                        else -> mView?.showError(message)
                    }
                }
            }
            LoginWithFbRequest().executeAsync(token.token, mResponseListenerLoginFb)
        }

    }

    override fun performFbWithUserData(email: String, phone: EntregoPhoneModel) {

    }
}
