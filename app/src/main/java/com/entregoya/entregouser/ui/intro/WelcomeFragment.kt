package com.entregoya.entregouser.ui.intro

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.entregouser.R
import com.entregoya.entregouser.mvp.view.BaseMvpFragment
import com.entregoya.entregouser.ui.auth.AuthActivity
import com.entregoya.entregouser.ui.delivery.create.RootActivity
import com.entregoya.entregouser.ui.intro.registration.RegistrationFbActivity
import com.entregoya.entregouser.ui.registration.RegistrationActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : BaseMvpFragment<WelcomeContract.View, WelcomeContract.Presenter>(),
        WelcomeContract.View {
    override fun showRegistrationWithFb(fbToken: String) {
        activity.startActivity(RegistrationFbActivity.getIntent(activity, fbToken))
    }

    override var mPresenter: WelcomeContract.Presenter = WelcomePresenter()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_welcome, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        login_button.fragment = this
        welcome_log_fb_btn.setOnClickListener {
            mPresenter.loginWithFb()
        }


        welcome_login_btn.setOnClickListener { startEntregoAuth() }
        welcome_registr_btn.setOnClickListener { startRegistration() }
    }

    private fun startRegistration() {
        val intent = Intent(context, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun startEntregoAuth() {
        val intent = AuthActivity.getIntent(activity)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter.performActivityResult(requestCode, resultCode, data)
    }

    override fun registerFacebookCallback(manager: CallbackManager, callback: FacebookCallback<LoginResult>) {
        login_button.registerCallback(manager, callback)
    }


    override fun showRootView() {
        startActivity(RootActivity.getIntent(activity))
        activity.finish()
    }

    override fun performFbClick() {
        login_button.performClick()
    }

    override fun showEmailPhoneInput() {

    }
}
