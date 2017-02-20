package com.entrego.entregouser.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.entrego.entregouser.R
import com.entrego.entregouser.storage.preferences.PreferencesManager
import com.entrego.entregouser.ui.delivery.create.RootActivity
import com.entrego.entregouser.ui.intro.IntroActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({ checkToken() }, 1500)
    }

    private fun checkToken() {
        if (PreferencesManager.getTokenOrEmpty().isEmpty())
            startLoginScreen()
        else
            startRootActivity()


    }

    private fun startRootActivity() {
        startActivity(RootActivity.getIntent(this))
    }

    private fun startLoginScreen() {
        startActivity(Intent(this, IntroActivity::class.java))
    }
}
