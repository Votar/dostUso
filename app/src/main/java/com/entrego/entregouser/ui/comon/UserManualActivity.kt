package com.entrego.entregouser.ui.comon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.entrego.entregouser.R
import kotlinx.android.synthetic.main.navigation_toolbar.*

class UserManualActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_manual)
        nav_toolbar_back.setOnClickListener { onBackPressed() }
    }
}
