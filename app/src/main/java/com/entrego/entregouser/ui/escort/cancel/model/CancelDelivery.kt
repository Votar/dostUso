package com.entrego.entregouser.ui.escort.cancel.model

import android.os.Handler
import android.support.annotation.Nullable
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.response.EntregoResponse
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.POST


object CancelDelivery {

    const val END_POINT = ""

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(): Call<EntregoResponse>
    }

    interface CancelDeliveryListener {
        fun onSuccessCancel()
        fun onFailureCancel(message: String?, code: Int?)
    }

    var isRequested: Boolean = false

    fun executeAsync(token: String, deliveryId: Int, reason: String, @Nullable listener: CancelDeliveryListener?) {

        if (!isRequested) {
            isRequested = true
            Handler().postDelayed({
                listener?.onSuccessCancel()
                isRequested = false
            }, 1500)
        }
    }
}