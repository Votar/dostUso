package com.entregoya.entregouser.ui.profile.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.content.ContextCompat
import android.view.View
import com.entregoya.entregouser.R
import com.entregoya.entregouser.mvp.view.BaseMvpActivity
import com.entregoya.entregouser.storage.realm.models.CustomerProfileModel
import com.entregoya.entregouser.ui.profile.edit.photo.UploadPhotoActivity
import com.entregoya.entregouser.util.loadCustomerPicWithToken
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class EditProfileActivity : BaseMvpActivity<EditProfileContract.View, EditProfileContract.Presenter>(),
        EditProfileContract.View {

    companion object {
        fun getIntent(ctx: Context): Intent = Intent(ctx, EditProfileActivity::class.java)
    }

    override var mPresenter: EditProfileContract.Presenter = EditProfilePresenter()

    override fun getRootView(): View? = activity_edit_profile

    enum class FIELDS(val serializeName: String) {
        NAME("name"),
        EMAIL("email"),
        PHONE_CODE("phone.code"),
        PHONE_NUMBER("phone.number"),
        PASSWORD("password"),
        CONF_PASSWORD("password.conf")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        setupListeners()
        mPresenter.getUserProfile()
    }

    override fun onStart() {
        super.onStart()
        edit_profile_swipe.isEnabled = false
        val colorAccent = ContextCompat.getColor(this, R.color.colorAccent)
        val colorDarkBlue = ContextCompat.getColor(this, R.color.colorDarkBlue)
        edit_profile_swipe.setColorSchemeColors(colorAccent, colorDarkBlue)
    }

    fun setupLayouts() {

    }

    fun setupListeners() {
        edit_profile_btn_save.setOnClickListener { postProfile() }
        edit_profile_btn_save_pass.setOnClickListener { postNewPassword() }
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        edit_profile_change_password.setOnClickListener {
            edit_profile_change_password.visibility = View.GONE
            edit_profile_ll_passwords.visibility = View.VISIBLE
            edit_profile_btn_save_pass.visibility = View.VISIBLE
            edit_profile_edit_password.requestFocus()
        }
        edit_profile_user_pic_holder.setOnClickListener {
            startActivity(Intent(EditProfileActivity@ this, UploadPhotoActivity::class.java))
        }
    }

    private fun postNewPassword() {
        val password = edit_profile_edit_password.text.toString()
        val confPassword = edit_profile_edit_password_conf.text.toString()
        mPresenter.postEditPassword(password, confPassword)
    }

    fun postProfile() {
        val name = edit_profile_edit_name.text.toString()
        val email = edit_profile_edit_email.text.toString()
        val code = edit_profile_edit_phone_code.text.toString()
        val phone = edit_profile_edit_phone.text.toString()
        mPresenter.postEditProfile(name, email, code, phone)
    }

    override fun onBackPressed() {
        NavUtils.navigateUpFromSameTask(this)
    }

    override fun showProgress() {
        edit_profile_swipe.isEnabled = true
        edit_profile_swipe.isRefreshing = true
    }

    override fun hideProgress() {
        edit_profile_swipe.isEnabled = false
        edit_profile_swipe.isRefreshing = false
    }

    override fun showUserProfile(profile: CustomerProfileModel) {

        profile.email?.let {
            edit_profile_edit_email.setText(it)
        }
        profile.name?.let {
            edit_profile_edit_name.setText(it)
        }
        profile.phone?.code?.let {
            edit_profile_edit_phone_code.setText(it)
        }
        profile.phone?.number?.let {
            edit_profile_edit_phone.setText(it)
        }
        edit_profile_user_pic_holder.loadCustomerPicWithToken()
    }


    override fun setFieldError(field: FIELDS, message: String?) {
        when (field) {
            FIELDS.EMAIL -> edit_profile_il_email.error = message

            FIELDS.NAME -> edit_profile_il_name.error = message

            FIELDS.PHONE_NUMBER -> edit_profile_il_phone.error = message

            FIELDS.PHONE_CODE -> edit_profile_il_phone_code.error = message

            FIELDS.PASSWORD -> edit_profile_il_password.error = message

            FIELDS.CONF_PASSWORD -> edit_profile_il_password_conf.error = message

            else -> showMessage(field.toString() + " " + message)
        }
    }

    override fun clearFieldsError() {
        edit_profile_il_email.error = null
        edit_profile_il_name.error = null
        edit_profile_il_phone.error = null
        edit_profile_il_phone_code.error = null
        edit_profile_il_password.error = null
        edit_profile_il_password_conf.error = null
    }

}
