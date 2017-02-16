package com.entrego.entregouser.ui.delivery.finish.model

import android.os.Handler
import android.support.annotation.Nullable
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.response.EntregoResponse
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.POST


object FinishDelivery {

    const val END_POINT = "/"

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(): Call<EntregoResponse>
    }

    interface ResponseListener {
        fun onSuccess()
        fun onFailure(message: String?, code: Int?)
    }

    var isRequested: Boolean = false

    fun executeAsync(token: String, comment: String, rating: Float, @Nullable listener: ResponseListener?): Call<EntregoResponse> {


        val request = ApiCreator.get()
                .create(Request::class.java)
                .parameters()

        if (!isRequested) {
            isRequested = true
            Handler().postDelayed({
                listener?.onSuccess()
                isRequested = false
            }, 1500)
        }

        return request
    }
}