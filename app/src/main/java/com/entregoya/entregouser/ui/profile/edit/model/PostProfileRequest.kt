package com.entregoya.entregouser.ui.profile.edit.model

import com.entregoya.entregouser.storage.realm.models.CustomerProfileModel
import com.entregoya.entregouser.web.api.ApiContract
import com.entregoya.entregouser.web.api.ApiCreator
import com.entregoya.entregouser.web.api.EntregoApi
import com.entregoya.entregouser.web.model.response.BaseEntregoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

class PostProfileRequest {

    companion object {
        private const val END_POINT = "customer/user/change"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Body body: CustomerProfileModel): Call<BaseEntregoResponse>
    }

    interface ResponseListener {
        fun onSuccessResponse(updatedProfile: CustomerProfileModel)
        fun onFailureResponse(code: Int?, message: String?)
    }

    fun requestAsync(token: String, userProfileModel: CustomerProfileModel, listener: ResponseListener?): Call<BaseEntregoResponse> {
        val request = ApiCreator.get()
                .create(Request::class.java)
                .parameters(token, userProfileModel)

        request.enqueue(object : Callback<BaseEntregoResponse> {
            override fun onResponse(call: Call<BaseEntregoResponse>?, response: Response<BaseEntregoResponse>?) {
                if (response?.body() != null)
                    response.body().apply {
                        when (code) {
                            ApiContract.RESPONSE_OK -> listener?.onSuccessResponse(userProfileModel)
                            else -> listener?.onFailureResponse(code, message)
                        }
                    }
                else
                    listener?.onFailureResponse(null, null)
            }

            override fun onFailure(call: Call<BaseEntregoResponse>?, t: Throwable?) {
                listener?.onFailureResponse(null, null)
            }
        })

        return request
    }


}