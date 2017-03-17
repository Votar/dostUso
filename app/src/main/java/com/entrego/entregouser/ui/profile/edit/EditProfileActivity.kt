package com.entrego.entregouser.ui.profile.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.content.ContextCompat
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.storage.realm.models.CustomerProfileModel
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
        Glide.with(this)
                .load("https://s-media-cache-ak0.pinimg.com/736x/a6/92/ae/a692aeb7da83d36f5d8e30dfa0801f9a.jpg")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.ic_user_pic_holder)
                .into(edit_profile_user_pic_holder)

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
    }


    override fun setFieldError(field: FIELDS, message: String?) {
        when (field) {
            FIELDS.EMAIL -> edit_profile_il_email.error = message

            FIELDS.NAME -> edit_profile_il_name.error = message

            FIELDS.PHONE_NUMBER -> edit_profile_il_phone.error = message

            FIELDS.PHONE_CODE -> edit_profile_il_phone_code.error = message

            FIELDS.PASSWORD -> edit_profile_il_password.error = message

            FIELDS.CONF_PASSWORD -> edit_profile_edit_password_conf.error = message

            else -> showMessage(field.toString() + " " + message)
        }
    }

    override fun clearFieldsError() {
        edit_profile_il_email.error = null
        edit_profile_il_name.error = null
        edit_profile_il_phone.error = null
        edit_profile_il_phone_code.error = null
        edit_profile_il_password.error = null
        edit_profile_edit_password_conf.error = null
    }
//        edit_profile_btn_save_pass.setOnClickListener {
//
//            edit_profile_il_password.error = null
//            edit_profile_il_password_conf.error = null
//            val pass = edit_profile_edit_password.text.toString()
//            val confPass = edit_profile_edit_password_conf.text.toString()
//
//            if (TextUtils.isEmpty(pass)) {
//                edit_profile_il_password.error = getString(R.string.error_empty_fields)
//                edit_profile_edit_password.requestFocus()
//            } else if (pass != confPass) {
//                edit_profile_il_password_conf.error = getString(R.string.error_passwords_not_equals)
//                edit_profile_edit_password_conf.requestFocus()
//
//            } else {
//                showProgress()
//            }
//        }
//    }
//
//
//    fun saveData() {
//
//        showProgress()
//        edit_profile_il_name.error = null
//        edit_profile_il_email.error = null
//        edit_profile_il_phone.error = null
//        edit_profile_il_phone_code.error = null
//
//        UserProfile.update(applicationContext,
//                edit_profile_edit_email.text.toString(),
//                edit_profile_edit_name.text.toString(),
//                edit_profile_edit_phone_code.text.toString(),
//                edit_profile_edit_phone.text.toString(),
//                object : UserProfile.ResultUpdateListener {
//                    override fun onFieldError(field: FieldErrorResponse) {
//
//                        hideProgress()
//                        when (field.field) {
//                            FIELDS.EMAIL -> {
//                                edit_profile_il_email.error = field.message
//                            }
//                            FIELDS.NAME -> {
//                                edit_profile_il_name.error = field.message
//                            }
//                            FIELDS.PHONE_NUMBER -> {
//                                edit_profile_il_phone.error = field.message
//                            }
//                            FIELDS.PHONE_CODE -> {
//                                edit_profile_il_phone_code.error = field.message
//                            }
//                            else -> showMessage(field.field + " " + field.message)
//                        }
//
//                    }
//
//                    override fun onFailureUpdate(message: String) {
//                        hideProgress()
//                        UserMessageUtil.showSnackMessage(activity_edit_profile, message)
//                    }
//
//                    override fun onSuccessUpdate(userProfile: UserProfileModel) {
//                        hideProgress()
//                        UserMessageUtil.showSnackMessage(activity_edit_profile, getString(R.string.success_profile_updated))
//                    }
//
//                }
//        )
//    }
}
