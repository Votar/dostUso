package com.entrego.entregouser.ui.auth.restore

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.entrego.entregouser.R
import com.entrego.entregouser.util.loading
import com.entrego.entregouser.util.showSnack
import com.entrego.entregouser.ui.auth.restore.model.RestoreModel
import entrego.com.android.web.model.response.CommonResponseListener
import kotlinx.android.synthetic.main.activity_restore_password.*

class RestorePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore_password)

        respore_password_btn.setOnClickListener {
            if (restore_edit_email.text.isEmpty()) {
                restore_ll_email.error = getString(R.string.error_empty_fields)
            } else {
                mProgress = ProgressDialog(this)
                mProgress?.loading()
                RestoreModel.restorePassword(restore_edit_email.text.toString(), mRestoreListener)
            }
        }
    }

    var mProgress: ProgressDialog? = null
    val mRestoreListener = object : CommonResponseListener {
        override fun onSuccessResponse() {
            mProgress?.hide()
            startActivity(Intent(applicationContext, SuccessRestoreActivity::class.java))
        }

        override fun onFailureResponse(code: Int?, message: String?) {
            mProgress?.hide()
            activity_restore_password.showSnack(message)
        }

    }
}
