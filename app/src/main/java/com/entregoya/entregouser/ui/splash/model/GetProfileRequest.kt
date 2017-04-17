package com.entregoya.entregouser.ui.splash.model

import com.entregoya.entregouser.storage.realm.models.CustomerProfileModel
import com.entregoya.entregouser.web.api.ApiCreator
import com.entregoya.entregouser.web.api.EntregoApi
import com.entregoya.entregouser.web.model.response.profile.GetProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

class GetProfileRequest {

    companion object {
        private const val END_POINT = "customer/user"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @GET(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String): Call<GetProfileResponse>
    }

    interface ResponseListener {
        fun onSuccessResponse(profileJson: CustomerProfileModel)
        fun onFailureResponse(code: Int?, message: String?)
    }

    fun requestAsync(token: String, listener: ResponseListener?): Call<GetProfileResponse> {
        val request = ApiCreator.get()
                .create(Request::class.java)
                .parameters(token)

        request.enqueue(object : Callback<GetProfileResponse> {
            override fun onFailure(call: Call<GetProfileResponse>?, t: Throwable?) {
                listener?.onFailureResponse(null, t?.message)
            }

            override fun onResponse(call: Call<GetProfileResponse>?, response: Response<GetProfileResponse>?) {
                if (response?.body() != null)
                    response.body().apply {
                        when (code) {
                            0 -> listener?.onSuccessResponse(payload)
                            else -> listener?.onFailureResponse(code, message)
                        }
                    }
                else
                    listener?.onFailureResponse(null, null)
            }
        })

        return request
    }


}