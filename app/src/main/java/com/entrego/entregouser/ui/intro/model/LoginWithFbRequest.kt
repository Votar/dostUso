package com.entrego.entregouser.ui.intro.model

import com.entrego.entregouser.web.api.ApiContract
import com.entrego.entregouser.web.api.ApiCreator
import com.entrego.entregouser.web.api.EntregoApi
import com.entrego.entregouser.web.model.request.auth.AuthWithFbBody
import com.entrego.entregouser.web.model.response.BaseEntregoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


class LoginWithFbRequest {
    companion object {
        const val END_POINT = "loginFb"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Body parameters: AuthWithFbBody): Call<BaseEntregoResponse>
    }

    interface LoginWithFbRequestListener {
        fun onSuccessLoginWithFbRequest(token: String)
        fun onFailureLoginWithFbRequest(code: Int?, message: String?)
    }

    fun executeAsync(token: String, listener: LoginWithFbRequestListener?) {
        ApiCreator.get()
                .create(Request::class.java)
                .parameters(AuthWithFbBody(token))
                .enqueue(object : Callback<BaseEntregoResponse> {
                    override fun onResponse(call: Call<BaseEntregoResponse>?, response: Response<BaseEntregoResponse>?) {
                        if (response?.body() != null)
                            response.body().apply {
                                when (code) {
                                    ApiContract.RESPONSE_OK -> {
                                        listener?.onSuccessLoginWithFbRequest(response
                                                .headers()
                                                .get(EntregoApi.TOKEN)
                                        )
                                    }
                                    else -> listener?.onFailureLoginWithFbRequest(code, message)
                                }
                            }
                        else listener?.onFailureLoginWithFbRequest(null, null)
                    }

                    override fun onFailure(call: Call<BaseEntregoResponse>?, t: Throwable?) {
                        listener?.onFailureLoginWithFbRequest(null, null)
                    }

                })
    }
}
