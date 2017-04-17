package com.entregoya.entregouser.ui.comon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.entregoya.entregouser.R
import kotlinx.android.synthetic.main.navigation_toolbar.*

class AboutEntregoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_entrego)
        nav_toolbar_back.setOnClickListener { onBackPressed() }
    }
}
