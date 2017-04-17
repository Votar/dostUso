package com.entrego.entregouser.ui.payment.model

import com.entrego.entregouser.web.api.ApiContract
import com.entrego.entregouser.web.api.ApiCreator
import com.entrego.entregouser.web.api.EntregoApi
import com.entrego.entregouser.web.model.request.profile.DeleteCardBody
import com.entrego.entregouser.web.model.response.BaseEntregoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


class DeleteCardRequest {
    companion object {
        const val END_POINT = "customer/payment/card/delete"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Body body: DeleteCardBody): Call<BaseEntregoResponse>
    }

    interface DeleteCardRequestListener {
        fun onSuccessDeleteCardRequest()
        fun onFailureDeleteCardRequest(code: Int?, message: String?)
    }

    fun executeAsync(token: String, cardToken: String, listener: DeleteCardRequestListener?) {

        ApiCreator.get()
                .create(Request::class.java)
                .parameters(token, DeleteCardBody(cardToken))
                .enqueue(object : Callback<BaseEntregoResponse> {
                    override fun onFailure(call: Call<BaseEntregoResponse>?, t: Throwable?) {
                        listener?.onFailureDeleteCardRequest(null, null)
                    }

                    override fun onResponse(call: Call<BaseEntregoResponse>?, response: Response<BaseEntregoResponse>?) {
                        if (response?.body() != null)
                            response.body()?.apply {
                                when (code) {
                                    ApiContract.RESPONSE_OK -> listener?.onSuccessDeleteCardRequest()
                                    else -> listener?.onFailureDeleteCardRequest(code, message)
                                }
                            }
                        else
                            listener?.onFailureDeleteCardRequest(null, null)
                    }
                })

    }

}