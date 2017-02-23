package com.entrego.entregouser.ui.profile.edit

import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter


class EditProfilePresenter : BaseMvpPresenter<EditProfileContract.View>(),
        EditProfileContract.Presenter {


    override fun postEditProfile(name: String, email: String, code: String, phone: String) {
        mView?.clearFieldsError()

        mView?.showProgress()

    }

    override fun postEditPassword(password: String, confPassword: String) {
        mView?.clearFieldsError()
        if (password.isEmpty()) {
            mView?.setFieldError(EditProfileActivity.FIELDS.PASSWORD, mView?.getAppContext()?.getString(R.string.error_empty_fields))
        } else if (password.equals(confPassword, true)) {
            mView?.setFieldError(EditProfileActivity.FIELDS.CONF_PASSWORD, mView?.getAppContext()?.getString(R.string.error_passwords_not_equals))
        } else {
            mView?.showProgress()
        }
    }

    override fun getUserProfile() {
        mView?.showProgress()

    }
}