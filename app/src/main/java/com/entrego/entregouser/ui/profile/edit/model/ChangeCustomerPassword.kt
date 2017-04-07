package com.entrego.entregouser.ui.profile.edit.model

import com.entrego.entregouser.web.api.ApiContract
import com.entrego.entregouser.web.api.ApiCreator
import com.entrego.entregouser.web.api.EntregoApi
import com.entrego.entregouser.web.model.request.profile.ChangePasswordBody
import com.entrego.entregouser.web.model.response.BaseEntregoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


class ChangeCustomerPassword {
    companion object {
        const val END_POINT = "customer/user/change/password"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Body newPassword: ChangePasswordBody): Call<BaseEntregoResponse>
    }

    interface ChangeCustomerPasswordListener {
        fun onSuccessChangeCustomerPassword()
        fun onFailureChangeCustomerPassword(code: Int?, message: String?)
    }

    fun executeAsync(token: String, newPassword: String, listener: ChangeCustomerPasswordListener?) {
        ApiCreator
                .get()
                .create(Request::class.java)
                .parameters(token, ChangePasswordBody(newPassword))
                .enqueue(
                        object : Callback<BaseEntregoResponse> {
                            override fun onFailure(call: Call<BaseEntregoResponse>?, t: Throwable?) {
                                listener?.onFailureChangeCustomerPassword(null, null)
                            }

                            override fun onResponse(call: Call<BaseEntregoResponse>?,
                                                    response: Response<BaseEntregoResponse>?) {
                                if (response?.body() != null) {
                                    when (response.body().code) {
                                        ApiContract.RESPONSE_OK -> listener?.onSuccessChangeCustomerPassword()
                                        else ->
                                            listener?.onFailureChangeCustomerPassword(
                                                    response.body().code,
                                                    response.body().message
                                            )
                                    }
                                    listener?.onSuccessChangeCustomerPassword()
                                } else listener?.onFailureChangeCustomerPassword(null, null)
                            }

                        }
                )
    }

}