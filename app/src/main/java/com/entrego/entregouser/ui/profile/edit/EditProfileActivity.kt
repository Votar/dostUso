package com.entrego.entregouser.ui.profile.edit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.entrego.entregouser.R
import kotlinx.android.synthetic.main.navigation_toolbar.*

class EditProfileActivity : AppCompatActivity() {

    companion object {
        val RQT_CODE = 0x691
    }

    object FIELDS {
        val NAME = "name"
        val EMAIL = "email"
        val PHONE_CODE = "phone.code"
        val PHONE_NUMBER = "phone.number"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        nav_toolbar_back.setOnClickListener { onBackPressed() }
    }
//
//    override fun onStart() {
//        super.onStart()
//        val userProfile = UserProfile.getProfile(this)
//        if (userProfile != null) {
//            logd(userProfile.toString())
//            setupView(userProfile)
//        } else requestUserProfile()
//    }
//
//
//    fun setupView(profile: UserProfileModel) {
//
//        Glide.with(this)
//                .load("https://s-media-cache-ak0.pinimg.com/736x/a6/92/ae/a692aeb7da83d36f5d8e30dfa0801f9a.jpg")
//                .skipMemoryCache(true)
//                .diskCacheStrategy( DiskCacheStrategy.NONE )
//                .error(R.drawable.ic_user_pic_holder)
//                .into(edit_profile_user_pic_holder)
//
//        edit_profile_edit_email.setText(profile.email)
//        edit_profile_edit_name.setText(profile.name)
//        edit_profile_edit_phone_code.setText(profile.phone.code)
//        edit_profile_edit_phone.setText(profile.phone.number)
//
//
//        edit_profile_btn_save.setOnClickListener { saveData() }
//        edit_profile_change_password.setOnClickListener {
//            edit_profile_change_password.visibility = View.GONE
//            edit_profile_ll_passwords.visibility = View.VISIBLE
//            edit_profile_btn_save_pass.visibility = View.VISIBLE
//            edit_profile_edit_password.requestFocus()
//
//        }
//
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
//
//
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
//
//
//    }
//
//    var progress: ProgressDialog? = null
//    fun showProgress() {
//        progress = ProgressDialog(this)
//        progress?.loading()
//
//    }
//
//    fun hideProgress() {
//        progress?.dismiss()
//    }
//
//    fun requestUserProfile() {
//
//        showProgress()
//        UserProfile.refresh(this, object : UserProfile.ResultRefreshListener {
//            override fun onSuccessRefresh(userProfile: UserProfileModel) {
//                hideProgress()
//                setupView(userProfile)
//            }
//
//            override fun onFailureRefresh(message: String?) {
//                hideProgress()
//                showMessage(message)
//                finish()
//            }
//
//        })
//    }
//
//
//    fun showMessage(message: String?) {
//
//        UserMessageUtil.showSnackMessage(activity_edit_profile, message)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//
//        when (item?.itemId) {
//            android.R.id.home -> NavUtils.navigateUpFromSameTask(this)
//
//        }
//        return super.onOptionsItemSelected(item)
//
//    }
}
