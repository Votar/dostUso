package com.entrego.entregouser.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.entrego.entregouser.R
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.storage.realm.models.CustomerProfileModel
import com.entrego.entregouser.ui.delivery.create.RootActivity
import com.entrego.entregouser.ui.intro.IntroActivity
import com.entrego.entregouser.ui.splash.model.GetProfileRequest
import com.entrego.entregouser.util.logd

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({ checkToken() }, 500)
    }

    private fun checkToken() {
        val token = EntregoStorage.getTokenOrEmpty()
        GetProfileRequest().requestAsync(token, object : GetProfileRequest.ResponseListener {
            override fun onSuccessResponse(profileJson: CustomerProfileModel) {
                EntregoStorage.saveProfile(profileJson)
                startRootActivity()
            }

            override fun onFailureResponse(code: Int?, message: String?) {
                logd(message)
                startLoginScreen()
            }
        })
    }

    private fun startRootActivity() {
        startActivity(RootActivity.getIntent(this))
    }

    private fun startLoginScreen() {
        startActivity(Intent(this, IntroActivity::class.java))
    }
}
