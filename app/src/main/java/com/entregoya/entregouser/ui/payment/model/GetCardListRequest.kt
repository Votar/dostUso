package com.entregoya.entregouser.ui.payment.model

import com.entregoya.entregouser.web.api.EntregoApi
import com.entregoya.entregouser.web.model.response.BaseEntregoResponse
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

class GetCardListRequest {
    companion object {
        const val END_POINT = "/"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String): Call<BaseEntregoResponse>
    }

    interface GetCardListListener {
        fun onSuccessGetCardList()
        fun onFailureGetCardList(code: Int?, message: String?)
    }

    fun executeAsync(token: String) {
    }

}