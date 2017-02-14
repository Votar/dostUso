package com.entrego.entregouser.ui.registration

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.registration.presenter.IRegistrationPresenter
import com.entrego.entregouser.ui.registration.presenter.RegistrationPresenter
import com.entrego.entregouser.ui.registration.view.IRegistrationView
import com.entrego.entregouser.util.loading
import com.entrego.entregouser.util.showSnack
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity(), IRegistrationView {


    var presenter: IRegistrationPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        presenter = RegistrationPresenter(this)
        setupDefaultListeners()
    }

    override fun setErrorEmailRegistered() {
        registration_edit_email.requestFocus()
        registration_il_email.error = getString(R.string.error_email_registered)
    }

    override fun setErrorName(message: String) {
        registration_edit_name.requestFocus()
        registration_name_il.error = message
    }

    override fun setErrorPhoneCode(message: String) {
        registration_edit_phone_code.requestFocus()
        registration_il_phone_code.error = message
    }

    override fun setErrorPhoneNumber(message: String) {

        registration_edit_phone.requestFocus()
        registration_il_phone.error = message
    }

    override fun setErrorConfPassword() {
        registration_edit_password_conf.requestFocus()
        registration_il_password_conf.error = getString(R.string.error_passwords_not_equals)
    }

    override fun successRegistration() {
        val intent = Intent(applicationContext, SuccessRegistrationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun setErrorEmail(message: String) {
        registration_edit_email.requestFocus()
        registration_il_email.error = message
    }

    override fun setErrorPassword(message: String) {
        registration_edit_password.requestFocus()
        registration_il_password.error = message
    }

    override fun setErrorPassword(message: Int) {
        registration_edit_password.requestFocus()
        registration_il_password.error = getString(message)
    }

    override fun showMessage(message: String?) {
        activity_registration.showSnack(message)
    }

    var progress: ProgressDialog? = null
    override fun showProgress() {
        progress = ProgressDialog(this)
        progress?.loading()
    }

    override fun hideProgress() {
        progress?.dismiss()
    }

    fun setupDefaultListeners() {

        registration_btn_ok.setOnClickListener {

            registration_name_il.error = null
            registration_il_email.error = null
            registration_il_password.error = null
            registration_il_password_conf.error = null
            registration_il_phone_code.error = null
            registration_il_phone.error = null


            presenter?.requestRegistration(
                    registration_edit_email.text.toString(),
                    registration_edit_name.text.toString(),
                    registration_edit_password.text.toString(),
                    registration_edit_password_conf.text.toString(),
                    registration_edit_phone_code.text.toString(),
                    registration_edit_phone.text.toString()
            )

        }
        registration_btn_move_login.setOnClickListener {
            NavUtils.navigateUpFromSameTask(this)
        }
    }

}
