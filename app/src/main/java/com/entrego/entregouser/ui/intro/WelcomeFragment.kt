package com.entrego.entregouser.ui.intro

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.auth.AuthActivity
import com.entrego.entregouser.ui.registration.RegistrationActivity
import com.entrego.entregouser.util.showSnack
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment() {
    val mCallbackManager = com.facebook.CallbackManager.Factory.create();

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_welcome, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        login_button.fragment = this;
        welcome_log_fb_btn.setOnClickListener { login_button.performClick() }
        login_button.registerCallback(mCallbackManager, mLoginFbListener)
        welcome_login_btn.setOnClickListener { startEntregoAuth() }
        welcome_registr_btn.setOnClickListener { startRegistration() }
    }

    private fun startRegistration() {
        val intent = Intent(context, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun startEntregoAuth() {
        val intent = Intent(context, AuthActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    val mLoginFbListener = object : FacebookCallback<LoginResult> {
        override fun onSuccess(loginResult: LoginResult) {
            view?.showSnack(loginResult.accessToken.token)
        }

        override fun onCancel() {

        }

        override fun onError(e: FacebookException) {

        }
    }
}