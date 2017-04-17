package com.entregoya.entregouser.ui.profile.edit.photo.model

import android.graphics.Bitmap
import android.util.Base64
import com.entregoya.entregouser.web.api.ApiCreator
import com.entregoya.entregouser.web.api.EntregoApi
import com.entregoya.entregouser.web.model.request.profile.UploadPhotoBody
import com.entregoya.entregouser.web.model.response.BaseEntregoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import java.io.ByteArrayOutputStream


class UploadUserPic {
    companion object {
        const val END_POINT = "customer/user/change/photo"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Body body: UploadPhotoBody): Call<BaseEntregoResponse>
    }

    interface UploadUserPicListener {
        fun onSuccessUploadUserPic()
        fun onFailureUploadUserPic(code: Int?, message: String?)
    }

    fun executeAsync(token: String, picture: Bitmap, listener: UploadUserPicListener?) {

        val baos = ByteArrayOutputStream()
        picture.compress(Bitmap.CompressFormat.JPEG, 100, baos) //bm is the bitmap object
        val byteArray = baos.toByteArray()
        val photoEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT)

        ApiCreator.get()
                .create(Request::class.java)
                .parameters(token, UploadPhotoBody(photoEncoded))
                .enqueue(object : Callback<BaseEntregoResponse> {
                    override fun onResponse(call: Call<BaseEntregoResponse>?, response: Response<BaseEntregoResponse>?) {
                        if (response?.body() != null)
                            response.body()?.apply {
                                when (code) {
                                    0 -> listener?.onSuccessUploadUserPic()
                                    else -> listener?.onFailureUploadUserPic(code, null)
                                }
                            }
                        else listener?.onFailureUploadUserPic(null, null)
                    }

                    override fun onFailure(call: Call<BaseEntregoResponse>?, t: Throwable?) {
                        listener?.onFailureUploadUserPic(null, null)
                    }
                })


    }

}