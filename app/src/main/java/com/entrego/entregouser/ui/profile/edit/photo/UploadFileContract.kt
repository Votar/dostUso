package com.entrego.entregouser.ui.profile.edit.photo

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView

interface UploadFileContract {
    interface View : IBaseMvpView {
        fun showProgress()
        fun hideProgress()
        fun successUpload()
        fun getActivityContext(): Activity
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun handleResultActivity(requestCode: Int, resultCode: Int, data: Intent?)
        fun pickPhotoFromGallery()
        fun takePhotoFromCamera()
        fun uploadFileToServer(token: String, picture: Bitmap)
    }
}