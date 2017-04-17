package com.entregoya.entregouser.ui.auth.presenter

import android.os.Handler
import android.os.Looper
import com.entregoya.entregouser.storage.EntregoStorage
import com.entregoya.entregouser.storage.realm.models.CustomerProfileModel
import com.entregoya.entregouser.ui.auth.model.EntregoAuth
import com.entregoya.entregouser.ui.auth.view.IAuthView
import com.entregoya.entregouser.ui.splash.model.GetProfileRequest
import com.entregoya.entregouser.web.model.response.CommonResponseListener
import entrego.com.android.ui.auth.presenter.IAuthPresenter


class AuthPresenter(val view: IAuthView) : IAuthPresenter {

    val listener = object : CommonResponseListener {
        override fun onSuccessResponse() {
            view.hideProgress()
            view.goToMainScreen()
        }

        override fun onFailureResponse(code: Int?, message: String?) {
            view.hideProgress()
            view.showMessage(message)
        }
    }

    override fun requestAuth(email: String, password: String) {
        val request = EntregoAuth(email, password)

        view.showProgress()
        request.requestAsync(object : CommonResponseListener {
            override fun onSuccessResponse() {
                view.hideProgress()
                view.goToMainScreen()
                EntregoStorage.setLastEmail(email)
                requestProfile()
            }

            override fun onFailureResponse(code: Int?, message: String?) {
                view.hideProgress()
                view.showMessage(message)
            }
        })

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

    override fun requestForgotPassword() {

        view.showProgress()
        Handler(Looper.getMainLooper()).postDelayed(
                {
                    view.hideProgress()
                    view.successRestorePassword()
                }, 1500)

    }
}