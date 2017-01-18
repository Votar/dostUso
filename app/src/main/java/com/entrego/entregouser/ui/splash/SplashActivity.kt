package com.entrego.entregouser.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.intro.IntroActivity
import com.entrego.entregouser.util.disableFirstStart
import com.entrego.entregouser.util.isFirstStart

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({ startLoginScreen() }, 1500)
    }

    private fun startLoginScreen() {
        startActivity(Intent(this, IntroActivity::class.java))
    }
}
