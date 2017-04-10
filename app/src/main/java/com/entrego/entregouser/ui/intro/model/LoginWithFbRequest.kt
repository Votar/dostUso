package com.entrego.entregouser.ui.intro.model

import com.entrego.entregouser.entity.EntregoPhoneModel
import com.entrego.entregouser.web.api.EntregoApi
import com.entrego.entregouser.web.model.response.BaseEntregoResponse
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


class LoginWithFbRequest {
    companion object {
        const val END_POINT = "/"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String): Call<BaseEntregoResponse>
    }

    interface LoginWithFbRequestListener {
        fun onSuccessLoginWithFbRequest()
        fun onFailureLoginWithFbRequest(code: Int?, message: String?)
    }

    fun executeAsync(token: String) {

    }
    fun executeAsync(token:String, email:String, phone:EntregoPhoneModel){

    }
}
