package com.entrego.entregouser.ui.profile.edit

import com.entrego.entregouser.R
import com.entrego.entregouser.entity.EntregoPhoneModel
import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.storage.realm.models.CustomerProfileModel
import com.entrego.entregouser.ui.profile.edit.model.ChangeCustomerPassword
import com.entrego.entregouser.ui.profile.edit.model.PostProfileRequest
import com.entrego.entregouser.web.api.ApiContract


class EditProfilePresenter : BaseMvpPresenter<EditProfileContract.View>(),
        EditProfileContract.Presenter {

    val mToken = EntregoStorage.getTokenOrEmpty()
    val mPostProfileResponseListener = object : PostProfileRequest.ResponseListener {
        override fun onSuccessResponse(updatedProfile: CustomerProfileModel) {
            mView?.hideProgress()
            mView?.showMessage(R.string.success_profile_update)
            EntregoStorage.saveProfile(updatedProfile)
        }

        override fun onFailureResponse(code: Int?, message: String?) {
            mView?.hideProgress()
            mView?.showError(message)
        }

    }

    val mEditPasswordListener = object : ChangeCustomerPassword.ChangeCustomerPasswordListener {
        override fun onSuccessChangeCustomerPassword() {
            mView?.showMessage(R.string.success_change_password)
            mView?.hideProgress()
        }

        override fun onFailureChangeCustomerPassword(code: Int?, message: String?) {
            when (code) {
                ApiContract.RESPONSE_INVALID_TOKEN -> mView?.onLogout()
                else -> mView?.showError(message)
            }
            mView?.hideProgress()
        }

    }

    override fun postEditProfile(name: String, email: String, code: String, phone: String) {
        mView?.clearFieldsError()
        mView?.showProgress()

        val userProfile = CustomerProfileModel(email, name, EntregoPhoneModel(code, phone))
        PostProfileRequest().requestAsync(mToken, userProfile, mPostProfileResponseListener)
    }

    override fun postEditPassword(password: String, confPassword: String) {
        mView?.clearFieldsError()
        if (password.isEmpty()) {
            mView?.setFieldError(EditProfileActivity.FIELDS.PASSWORD, mView?.getAppContext()?.getString(R.string.error_empty_fields))
        } else if (!password.equals(confPassword, true)) {
            mView?.setFieldError(EditProfileActivity.FIELDS.CONF_PASSWORD, mView?.getAppContext()?.getString(R.string.error_passwords_not_equals))
        } else {
            mView?.showProgress()
            ChangeCustomerPassword().executeAsync(mToken, password, mEditPasswordListener)
        }
    }

    override fun getUserProfile() {
        EntregoStorage.getProfile()?.also { mView?.showUserProfile(it) }
    }
}