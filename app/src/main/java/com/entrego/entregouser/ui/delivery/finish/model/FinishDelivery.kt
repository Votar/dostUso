package com.entrego.entregouser.ui.delivery.finish.model

import android.os.Handler
import android.support.annotation.Nullable
import com.entrego.entregouser.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import com.entrego.entregouser.web.model.response.BaseEntregoResponse
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.POST


object FinishDelivery {

    const val END_POINT = "/"

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(): Call<BaseEntregoResponse>
    }

    interface ResponseListener {
        fun onSuccess()
        fun onFailure(message: String?, code: Int?)
    }

    var isRequested: Boolean = false

    fun executeAsync(token: String, comment: String, rating: Float, @Nullable listener: ResponseListener?): Call<BaseEntregoResponse> {


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