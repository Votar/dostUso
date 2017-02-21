package com.entrego.entregouser.ui.delivery.finish

import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.entrego.entregouser.ui.delivery.finish.model.FinishDelivery
import com.entrego.entregouser.web.model.response.BaseEntregoResponse
import retrofit2.Call


class FinishDeliveryPresenter : FinishDeliveryContract.Presenter,
        BaseMvpPresenter<FinishDeliveryContract.View>() {

    var mLastRequest: Call<BaseEntregoResponse>? = null

    override fun sendDeliveryComment(message: String, rating: Float) {
        mLastRequest = FinishDelivery.executeAsync("", "", 0f, mFinishDeliveryListener)
    }

    override fun detachView() {
        super.detachView()
    }

    override fun cancelLastRequest() {
        mLastRequest?.cancel()
    }

    val mFinishDeliveryListener = object : FinishDelivery.ResponseListener {
        override fun onSuccess() {
            mView?.onSuccessFinished()
        }

        override fun onFailure(message: String?, code: Int?) {

        }
    }
}