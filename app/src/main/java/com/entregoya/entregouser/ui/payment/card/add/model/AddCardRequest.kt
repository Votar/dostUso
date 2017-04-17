package com.entregoya.entregouser.ui.payment.card.add.model

import android.support.annotation.Nullable
import com.entregoya.entregouser.web.api.ApiContract
import com.entregoya.entregouser.web.api.ApiCreator
import com.entregoya.entregouser.web.api.EntregoApi
import com.entregoya.entregouser.web.model.request.AddCardBody
import com.entregoya.entregouser.web.model.response.common.FieldError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

class AddCardRequest {

    //TODO:fix it
    companion object {
        const val END_POINT = "customer/payment/card"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String,
                       @Body body: AddCardBody): Call<EntregoAddCardResponse>
    }

    interface AddCardListener {
        fun onSuccessAddCard()
        fun onFailureAddCard(code: Int?, message: String?)
        fun onValidationError(fields: Array<FieldError>)
    }

    fun executeAsync(token: String, body: AddCardBody, @Nullable listener: AddCardListener?) {
        ApiCreator
                .get()
                .create(Request::class.java)
                .parameters(token, body)
                .enqueue(object : Callback<EntregoAddCardResponse> {
                    override fun onFailure(call: Call<EntregoAddCardResponse>?, t: Throwable?) {
                        listener?.onFailureAddCard(null, null)
                    }

                    override fun onResponse(call: Call<EntregoAddCardResponse>?, response: Response<EntregoAddCardResponse>?) {
                        if (response?.body() != null)
                            when (response.body().code) {
                                ApiContract.RESPONSE_OK -> listener?.onSuccessAddCard()
                                ApiContract.FIELD_ERROR -> listener?.onValidationError(response.body().fields)
                                else -> listener?.onFailureAddCard(response.body().code, response.body().message)
                            }
                        else
                            listener?.onFailureAddCard(null, null)
                    }

                })
    }
}