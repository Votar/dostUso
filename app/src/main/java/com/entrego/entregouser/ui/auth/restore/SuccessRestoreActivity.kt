package com.entrego.entregouser.ui.auth.restore

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.activity_success_restore.*

class SuccessRestoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_restore)
        succ_restore_btn_login.setOnClickListener {
            startActivity(Intent(applicationContext, AuthActivity::class.java))
        }
    }

    override fun onBackPressed() {
        NavUtils.navigateUpFromSameTask(this)
    }
}
