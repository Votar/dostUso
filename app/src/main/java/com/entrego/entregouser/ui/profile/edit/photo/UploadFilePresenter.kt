package com.entrego.entregouser.ui.profile.edit.photo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.ui.profile.edit.photo.model.UploadUserPic
import com.entrego.entregouser.web.api.ApiContract
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.util.*


class UploadFilePresenter : BaseMvpPresenter<UploadFileContract.View>(),
        UploadFileContract.Presenter {

    val mToken = EntregoStorage.getTokenOrEmpty()

    companion object {
        val RQT_CAMERA = 0x222
        val RQT_GALLERY = 0x333
    }

    var permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            mView?.getActivityContext()?.startActivityForResult(cameraIntent, RQT_CAMERA)
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
            mView?.showMessage("Permission Denied\n")
        }
    }

    override fun handleResultActivity(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null)
            if (requestCode == RQT_CAMERA) {
                val photo = data.extras?.get("data") as Bitmap
                uploadFileToServer(mToken, photo)
            } else if (requestCode == RQT_GALLERY) {

                val uri = data.data
                val photo = MediaStore.Images.Media.getBitmap(mView?.getActivityContext()?.contentResolver, uri)
                uploadFileToServer(mToken, photo)
            }
// else EasyImage.handleActivityResult(requestCode, resultCode, data, mView?.getActivityContext(), object : DefaultCallback() {
//                override fun onImagePicked(imageFile: File?, source: EasyImage.ImageSource?, type: Int) {
//                    val photo = BitmapFactory.decodeFile(imageFile?.path)
//                    uploadFileToServer(mToken, photo)
//                }
//            })
    }

    override fun pickPhotoFromGallery() {
        mView?.getActivityContext()?.apply {
            val intent = Intent()
// Show only images, no videos or anything else
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
// Always show the chooser (if there are multiple options available)
            startActivityForResult(Intent.createChooser(intent, getString(R.string.text_select_picture)), RQT_GALLERY)
        }
    }

    override fun takePhotoFromCamera() {
        TedPermission(mView?.getActivityContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA)//set permission
                .check()
    }

    var mResponseListener = object : UploadUserPic.UploadUserPicListener {
        override fun onSuccessUploadUserPic() {
            mView?.hideProgress()
            mView?.showMessage(R.string.text_photo_updated)
        }

        override fun onFailureUploadUserPic(code: Int?, message: String?) {
            mView?.hideProgress()
            when (code) {
                ApiContract.RESPONSE_INVALID_TOKEN -> mView?.onLogout()
                else -> mView?.showError(message)
            }
        }
    }

    override fun uploadFileToServer(token: String, picture: Bitmap) {
        mView?.showProgress()
        UploadUserPic().executeAsync(token, picture, mResponseListener)
    }
}