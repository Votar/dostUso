package com.entrego.entregouser.ui.auth.model

import com.entrego.entregouser.storage.preferences.PreferencesManager
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.request.auth.AuthBody
import entrego.com.android.web.model.response.CommonResponseListener
import entrego.com.android.web.model.response.EntregoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

class EntregoAuth(val email: String, val password: String) {

    companion object {
        private const val END_POINT = ""
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Body body: AuthBody): Call<EntregoResponse>
    }

    fun requestAsync(listener: CommonResponseListener) {


        val body = AuthBody(email, password)
        ApiCreator.get()
                .create(Request::class.java)
                .parameters(body)
                .enqueue(object : Callback<EntregoResponse> {
                    override fun onResponse(call: Call<EntregoResponse>?, response: Response<EntregoResponse>?) {
                        when (response?.body()?.code) {
                            0 -> {
                                val token = response?.headers()?.get(EntregoApi.TOKEN)
                                token?.let {
                                    listener.onSuccessResponse()
                                    PreferencesManager.setToken(token)
                                }
                            }
                            else -> listener.onFailureResponse(response?.body()?.code, response?.body()?.message)
                        }
                    }
                    override fun onFailure(call: Call<EntregoResponse>?, t: Throwable?) {
                        listener.onFailureResponse(null, null)
                    }
                })
    }
}