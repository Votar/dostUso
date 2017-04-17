package com.entregoya.entregouser.ui.registration.model

import com.entregoya.entregouser.entity.EntregoPhoneModel
import com.entregoya.entregouser.web.api.ApiCreator
import com.entregoya.entregouser.web.api.EntregoApi
import com.entregoya.entregouser.web.model.request.registration.RegistrationBody
import com.entregoya.entregouser.web.model.response.CommonResponseListener
import com.entregoya.entregouser.web.model.response.common.FieldError
import com.entregoya.entregouser.web.model.response.registration.EntregoResultRegistration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


class EntregoRegistration(val email: String,
                          val name: String,
                          val password: String,
                          val phoneCode: String,
                          val phoneNumber: String) {

    interface ResultListener : CommonResponseListener {
        fun onValidationError(field: FieldError)
    }

    companion object {
        private const val END_POINT = "customer/user/register"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Body body: RegistrationBody): Call<EntregoResultRegistration>
    }

    fun requestAsync(listener: ResultListener) {

        val body = RegistrationBody(email, name, password, EntregoPhoneModel(phoneCode, phoneNumber))

        ApiCreator.get().create(Request::class.java)
                .parameters(body)
                .enqueue(object : Callback<EntregoResultRegistration> {
                    override fun onResponse(call: Call<EntregoResultRegistration>?, response: Response<EntregoResultRegistration>?) {
                        if (response?.body() != null) {
                            val result = response.body()!!
                            when (result.code) {
                                0 -> listener.onSuccessResponse()
                                1 -> if (result.fields.isNotEmpty()) {
                                    for (next in result.fields)
                                        listener.onValidationError(next)
                                }
                                else -> listener.onFailureResponse(result.code, result.message)
                            }
                        }
                    }

                    override fun onFailure(call: Call<EntregoResultRegistration>?, t: Throwable?) {
                        listener.onFailureResponse(null, t?.message)
                    }
                })
    }
}