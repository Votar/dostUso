package com.entregoya.entregouser.ui.auth.model

import com.entregoya.entregouser.storage.EntregoStorage
import com.entregoya.entregouser.storage.preferences.PreferencesManager
import com.entregoya.entregouser.web.api.ApiCreator
import com.entregoya.entregouser.web.api.EntregoApi
import com.entregoya.entregouser.web.model.response.BaseEntregoResponse
import com.entregoya.entregouser.web.model.response.CommonResponseListener
import entrego.com.android.web.model.request.auth.AuthBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

class EntregoAuth(val email: String, val password: String) {

    companion object {
        private const val END_POINT = "login"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Body body: AuthBody): Call<BaseEntregoResponse>
    }

    fun requestAsync(listener: CommonResponseListener): Call<BaseEntregoResponse> {

        val body = AuthBody(email, password)
        val request = ApiCreator.get()
                .create(Request::class.java)
                .parameters(body)

        request.enqueue(object : Callback<BaseEntregoResponse> {
            override fun onResponse(call: Call<BaseEntregoResponse>?, response: Response<BaseEntregoResponse>?) {
                when (response?.body()?.code) {
                    0 -> {
                        val token = response.headers()?.get(EntregoApi.TOKEN)
                        token?.let {
                            EntregoStorage.clear()
                            PreferencesManager.setToken(token)
                            listener.onSuccessResponse()
                        }
                    }
                    else -> listener.onFailureResponse(response?.body()?.code, response?.body()?.message)
                }
            }

            override fun onFailure(call: Call<BaseEntregoResponse>?, t: Throwable?) {
                listener.onFailureResponse(null, null)
            }
        })

        return request
    }
}