package com.entrego.entregouser.ui.profile.edit.model

import android.os.Handler
import com.entrego.entregouser.entity.profile.UserProfileModel
import com.entrego.entregouser.web.api.ApiCreator
import com.entrego.entregouser.web.model.response.delivery.list.EntregoDeliveryListResponse
import entrego.com.android.web.api.EntregoApi
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

class PostProfileRequest {

    companion object {
        private const val END_POINT = "/"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Body body: UserProfileModel): Call<EntregoDeliveryListResponse>
    }

    interface ResponseListener {
        fun onSuccessResponse()
        fun onFailureResponse(code: Int?, message: String?)
    }

    fun requestAsync(token: String, userProfileModel: UserProfileModel, listener: ResponseListener?): Call<EntregoDeliveryListResponse> {
        val request = ApiCreator.get()
                .create(Request::class.java)
                .parameters(token, userProfileModel)

        Handler().postDelayed({
            listener?.onSuccessResponse()
        }, 1500)

        return request
    }


}