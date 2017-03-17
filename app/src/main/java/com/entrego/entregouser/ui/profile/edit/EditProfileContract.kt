package com.entrego.entregouser.ui.profile.edit

import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView
import com.entrego.entregouser.storage.realm.models.CustomerProfileModel


interface EditProfileContract {
    interface View : IBaseMvpView {
        fun showProgress()
        fun hideProgress()
        fun showUserProfile(profile: CustomerProfileModel)
        fun setFieldError(field: EditProfileActivity.FIELDS, message: String?)
        fun clearFieldsError()
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun getUserProfile()
        fun postEditProfile(name: String, email: String, code: String, phone: String)
        fun postEditPassword(password: String, confPassword: String)
    }

}