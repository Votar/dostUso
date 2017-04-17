package com.entregoya.entregouser.ui.favorites

import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import com.entregoya.entregouser.R
import kotlinx.android.synthetic.main.navigation_toolbar.*

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
    }
}
