package com.entrego.entregouser.ui.registration

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.activity_success_registration.*

class SuccessRegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_registration)

        succ_reg_btn_login.setOnClickListener {
            startActivity(AuthActivity.getIntent(SuccessRegistrationActivity@ this))
        }
    }
}
