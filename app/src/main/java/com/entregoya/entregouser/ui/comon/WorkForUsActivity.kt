package com.entregoya.entregouser.ui.comon

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.widget.MediaController
import com.entregoya.entregouser.R
import kotlinx.android.synthetic.main.activity_work_for_us.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class WorkForUsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_for_us)
        setupListeners()

        work_for_us_videoview.setVideoURI(Uri.parse("https://dl.dropboxusercontent.com/s/gh3868qqcctg7w0/entreGO%20-%20Lo%20que%20quieras%2C%20cuando%20quieras.mp4?dl=0"))
        work_for_us_videoview.setMediaController(MediaController(this))
        work_for_us_videoview.requestFocus(0)
    }

    fun setupListeners() {
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        work_for_us_share.setOnClickListener{shareVideo()}

    }

    fun shareVideo(){

    }

}
