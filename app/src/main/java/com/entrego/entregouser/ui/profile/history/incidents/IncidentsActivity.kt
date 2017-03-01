package com.entrego.entregouser.ui.profile.history.incidents

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils

import com.entrego.entregouser.R
import kotlinx.android.synthetic.main.navigation_toolbar.*

class IncidentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incidents)
        setupLayouts()
    }

    private fun setupLayouts() {
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
    }
}
