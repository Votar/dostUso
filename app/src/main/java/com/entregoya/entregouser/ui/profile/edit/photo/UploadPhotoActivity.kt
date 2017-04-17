package com.entregoya.entregouser.ui.profile.edit.photo

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.View
import com.entregoya.entregouser.R
import com.entregoya.entregouser.mvp.view.BaseMvpActivity
import com.entregoya.entregouser.util.loading
import kotlinx.android.synthetic.main.activity_add_files.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class UploadPhotoActivity : BaseMvpActivity<UploadFileContract.View, UploadFileContract.Presenter>(),
        UploadFileContract.View {


    override fun getRootView(): View? = activity_add_files

    var mProgress: ProgressDialog? = null
    override fun showProgress() {
        mProgress = ProgressDialog(this)
        mProgress?.loading()
    }

    override fun hideProgress() {
        mProgress?.dismiss()
    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mPresenter.handleResultActivity(requestCode, resultCode, data)
    }


    override fun successUpload() {
    }

    override fun getActivityContext(): Activity = this

    override var mPresenter: UploadFileContract.Presenter = UploadFilePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_files)

        setupLayouts()
    }

    private fun setupLayouts() {
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        add_files_camera.setOnClickListener {
            mPresenter.takePhotoFromCamera()
        }
        add_files_gallery.setOnClickListener {
            mPresenter.pickPhotoFromGallery()
        }
    }
}
