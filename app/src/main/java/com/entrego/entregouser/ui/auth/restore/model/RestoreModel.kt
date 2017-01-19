package com.entrego.entregouser.ui.auth.restore.model

import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.request.auth.RestorePasswordBody
import entrego.com.android.web.model.response.CommonResponseListener
import entrego.com.android.web.model.response.EntregoResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

object RestoreModel {

    private const val END_POINT = ""

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Body body: RestorePasswordBody): Call<EntregoResult>
    }

    val request = ApiCreator.get().create(Request::class.java)
    fun restorePassword(email: String, listener: CommonResponseListener?) {
        request.parameters(RestorePasswordBody(email))
                .enqueue(object : Callback<EntregoResult> {
                    override fun onResponse(call: Call<EntregoResult>?, response: Response<EntregoResult>?) {
                        when (response?.body()?.code) {
                            0 -> listener?.onSuccessResponse()
                            else -> listener?.onFailureResponse(response?.body()?.code, response?.body()?.message)
                        }
                    }

                    override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                        listener?.onFailureResponse(null, null)
                    }
                })
    }
}