package com.entregoya.entregouser.ui.intro

import android.content.Intent
import com.entregoya.entregouser.entity.EntregoPhoneModel
import com.entregoya.entregouser.mvp.presenter.BaseMvpPresenter
import com.entregoya.entregouser.storage.EntregoStorage
import com.entregoya.entregouser.storage.preferences.PreferencesManager
import com.entregoya.entregouser.storage.realm.models.CustomerProfileModel
import com.entregoya.entregouser.ui.intro.model.LoginWithFbRequest
import com.entregoya.entregouser.ui.splash.model.GetProfileRequest
import com.entregoya.entregouser.web.api.ApiContract
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
                    requestProfile()
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

    fun requestProfile() {
        GetProfileRequest()
                .requestAsync(EntregoStorage.getTokenOrEmpty(),
                        object : GetProfileRequest.ResponseListener {
                            override fun onSuccessResponse(profileJson: CustomerProfileModel) {
                                EntregoStorage.saveProfile(profileJson)
                            }

                            override fun onFailureResponse(code: Int?, message: String?) {

                            }
                        })
    }

    override fun performFbWithUserData(email: String, phone: EntregoPhoneModel) {

    }
}
