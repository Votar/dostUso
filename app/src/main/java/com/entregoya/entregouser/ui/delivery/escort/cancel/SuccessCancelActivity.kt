package com.entregoya.entregouser.ui.delivery.escort.cancel

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import com.entregoya.entregouser.R
import com.entregoya.entregouser.ui.delivery.create.RootActivity
import kotlinx.android.synthetic.main.navigation_toolbar.*

class SuccessCancelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_cancelation)
        setSupportActionBar(navigation_toolbar)
        nav_toolbar_back.setOnClickListener({
            onRootScreen()
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        onRootScreen()
    }

    fun onRootScreen(){
        val intent = Intent(this, RootActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
