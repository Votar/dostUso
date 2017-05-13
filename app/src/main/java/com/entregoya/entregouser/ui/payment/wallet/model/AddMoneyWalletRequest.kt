package com.entregoya.entregouser.ui.payment.wallet.model

import com.entregoya.entregouser.web.api.ApiContract
import com.entregoya.entregouser.web.api.ApiCreator
import com.entregoya.entregouser.web.api.EntregoApi
import com.entregoya.entregouser.web.model.request.profile.AddMoneyWalletBody
import com.entregoya.entregouser.web.model.response.BaseEntregoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

class AddMoneyWalletRequest {
    companion object {
        const val END_POINT = "customer/payment/wallet"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Body body: AddMoneyWalletBody): Call<BaseEntregoResponse>
    }

    interface AddMoneyWalletRequestListener {
        fun onSuccessAddMoneyWalletRequest()
        fun onFailureAddMoneyWalletRequest(code: Int?, message: String?)
    }

    fun executeAsync(token: String, amount: Float, cardToken: String, listener: AddMoneyWalletRequestListener?) {

        val body = AddMoneyWalletBody(amount, cardToken)
        ApiCreator.get().create(Request::class.java)
                .parameters(token, body)
                .enqueue(object : Callback<BaseEntregoResponse> {
                    override fun onResponse(call: Call<BaseEntregoResponse>?, response: Response<BaseEntregoResponse>?) {
                        if (response?.body() != null)
                            response.body().apply {
                                when (code) {
                                    ApiContract.RESPONSE_OK -> {
                                        listener?.onSuccessAddMoneyWalletRequest()
                                    }
                                    else -> listener?.onFailureAddMoneyWalletRequest(code, message)
                                }
                            }
                        else
                            listener?.onFailureAddMoneyWalletRequest(null, null)
                    }

                    override fun onFailure(call: Call<BaseEntregoResponse>?, t: Throwable?) {
                        listener?.onFailureAddMoneyWalletRequest(null, null)
                    }
                })
    }

}