package com.entregoya.entregouser.ui.intro.registration

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import com.entregoya.entregouser.R
import com.entregoya.entregouser.ui.delivery.create.RootActivity
import com.entregoya.entregouser.ui.intro.registration.presenter.IRegistrationFbPresenter
import com.entregoya.entregouser.ui.intro.registration.presenter.RegistrationFbPresenter
import com.entregoya.entregouser.ui.intro.registration.view.IRegistrationFbView
import com.entregoya.entregouser.util.loading
import com.entregoya.entregouser.util.showSnack
import kotlinx.android.synthetic.main.activity_registration_fb.*

class RegistrationFbActivity : AppCompatActivity(), IRegistrationFbView {

    var presenter: IRegistrationFbPresenter? = null
    var mFbToken: String = ""

    companion object {
        const val KEY_FB_TOKEN = "ext_k_token"
        fun getIntent(ctx: Context, fbToken: String): Intent {
            val intent = Intent(ctx, RegistrationFbActivity::class.java)
            intent.putExtra(KEY_FB_TOKEN, fbToken)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_fb)
        presenter = RegistrationFbPresenter(this)
        deserializeIntent()
        setupDefaultListeners()
    }

    override fun setErrorEmailRegistered() {
        registration_edit_email.requestFocus()
        registration_il_email.error = getString(R.string.error_email_registered)
    }

    override fun setErrorPhoneRegistered() {
        registration_edit_phone.requestFocus()
        registration_il_phone.error = getString(R.string.registered_field)
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

    override fun successRegistration() {
        val intent = Intent(applicationContext, RootActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    override fun setErrorEmail(message: String) {
        registration_edit_email.requestFocus()
        registration_il_email.error = message
    }

    override fun showMessage(message: String?) {
        activity_registration.showSnack(message)
    }

    fun deserializeIntent() {
        if (intent.hasExtra(KEY_FB_TOKEN)) {
            mFbToken = intent.getStringExtra(KEY_FB_TOKEN)
        } else throw IllegalStateException("No facebook token in intent")
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
            registration_il_phone_code.error = null
            registration_il_phone.error = null


            presenter?.requestRegistration(
                    registration_edit_email.text.toString(),
                    registration_edit_name.text.toString(),
                    mFbToken,
                    registration_edit_phone_code.text.toString(),
                    registration_edit_phone.text.toString()
            )

        }
        registration_btn_move_login.setOnClickListener {
            NavUtils.navigateUpFromSameTask(this)
        }
    }

}
