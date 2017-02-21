package com.entrego.entregouser.web.arcitec

import com.entrego.entregouser.web.model.response.BaseEntregoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseRetrofitCallback<R : BaseEntregoResponse> : Callback<R>{

    override fun onResponse(call: Call<R>?, response: Response<R>?) {

    }

    override fun onFailure(call: Call<R>?, t: Throwable?) {

    }
}