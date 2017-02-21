package com.entrego.entregouser.ui.auth.presenter

import android.os.Handler
import android.os.Looper
import com.entrego.entregouser.ui.auth.model.EntregoAuth
import com.entrego.entregouser.ui.auth.view.IAuthView
import entrego.com.android.ui.auth.presenter.IAuthPresenter
import com.entrego.entregouser.web.model.response.CommonResponseListener


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
        request.requestAsync(listener)

        view.showProgress()
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