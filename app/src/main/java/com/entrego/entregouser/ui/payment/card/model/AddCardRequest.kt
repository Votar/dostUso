package com.entrego.entregouser.ui.payment.card.model

import android.os.Handler
import android.support.annotation.Nullable
import com.entrego.entregouser.web.api.EntregoApi
import com.entrego.entregouser.web.model.request.AddCardBody
import com.entrego.entregouser.web.model.response.BaseEntregoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

class AddCardRequest {

    //TODO:fix it
    companion object {
        const val END_POINT = "addCard"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String,
                       @Body body: AddCardBody): Call<BaseEntregoResponse>
    }

    interface AddCardListener {
        fun onSuccessAddCard()
        fun onFailureAddCard(message: String?, code: Int?)
    }

    fun executeAsync(token: String, body: AddCardBody, @Nullable listener: AddCardListener?) {

        Handler().postDelayed({ listener?.onSuccessAddCard() }, 1500)
        /*ApiCreator.get()
                .create(Request::class.java)
                .parameters(token, body)

                .enqueue(object : Callback<BaseEntregoResponse> {
                    override fun onFailure(call: Call<BaseEntregoResponse>?, t: Throwable?) {
                        listener?.onFailureAddCard(null, null)
                    }

                    override fun onResponse(call: Call<BaseEntregoResponse>?, response: Response<BaseEntregoResponse>?) {

                        if (response?.body() != null) {
                            response.body().apply {
                                when (code) {
                                    ApiContract.RESPONSE_OK -> listener?.onSuccessAddCard()
                                    else -> listener?.onFailureAddCard(message, code)
                                }
                            }
                        } else listener?.onFailureAddCard(null, null)
                    }

                })*/
    }
}