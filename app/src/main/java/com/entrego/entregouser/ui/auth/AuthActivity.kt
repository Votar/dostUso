package com.entrego.entregouser.ui.auth

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.entrego.entregouser.R
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.ui.auth.presenter.AuthPresenter
import com.entrego.entregouser.ui.auth.restore.RestorePasswordActivity
import com.entrego.entregouser.ui.auth.restore.SuccessRestoreActivity
import com.entrego.entregouser.ui.auth.view.IAuthView
import com.entrego.entregouser.ui.delivery.create.RootActivity
import com.entrego.entregouser.util.loading
import com.entrego.entregouser.util.showSnack
import entrego.com.android.ui.auth.presenter.IAuthPresenter
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity(), IAuthView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        setupListeners()
    }

    var progress: ProgressDialog? = null
    override fun showProgress() {
        progress = ProgressDialog(this)
        progress?.loading()
    }

    override fun hideProgress() {
        progress?.dismiss()
    }

    override fun showMessage(message: String?) {
        activity_registration?.showSnack(message)
    }

    val presenter: IAuthPresenter = AuthPresenter(this)

    override fun getContext(): Context {
        return applicationContext
    }


    override fun goToMainScreen() {
        EntregoStorage.clear()
        val intent = Intent(applicationContext, RootActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    override fun setErrorEmail(message: String) {
        auth_edit_email.requestFocus()
        auth_il_email.error = getString(R.string.ui_error_email)
    }

    override fun setErrorPassword(message: String) {
        auth_edit_email.requestFocus()
        auth_il_email.error = getString(R.string.ui_error_password)
    }

    override fun successRestorePassword() {
        startActivity(Intent(applicationContext, SuccessRestoreActivity::class.java))
    }

    fun setupListeners() {
        auth_btn_login.setOnClickListener {
            startAuth()
        }

        auth_edit_password.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event?.keyCode == KeyEvent.KEYCODE_ENTER)
                        && (event?.action == KeyEvent.ACTION_DOWN))) {
                    startAuth()
                    return true
                } else {
                    return false
                }
            }

        })

        auth_btn_forgot_pass.setOnClickListener { startRestoreActivity() }
    }

    private fun startRestoreActivity() {
        val intent = Intent(this, RestorePasswordActivity::class.java)
        startActivity(intent)
    }

    fun startAuth() {
        val email = auth_edit_email.text.toString()
        val password = auth_edit_password.text.toString()
        presenter.requestAuth(email, password)
    }
}
