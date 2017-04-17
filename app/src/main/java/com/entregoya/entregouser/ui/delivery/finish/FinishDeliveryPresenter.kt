package com.entregoya.entregouser.ui.delivery.finish

import com.entregoya.entregouser.mvp.presenter.BaseMvpPresenter
import com.entregoya.entregouser.storage.preferences.PreferencesManager
import com.entregoya.entregouser.ui.delivery.finish.model.FinishDelivery
import com.entregoya.entregouser.web.model.response.BaseEntregoResponse
import retrofit2.Call


class FinishDeliveryPresenter : FinishDeliveryContract.Presenter,
        BaseMvpPresenter<FinishDeliveryContract.View>() {


    var mLastRequest: Call<BaseEntregoResponse>? = null
    var mDeliveryId: Long = 0
    var mOrderId: Long = 0
    override fun sendDeliveryComment(message: String, rating: Int) {

        if (mDeliveryId == 0L)
            throw IllegalArgumentException("deliveryId is null")
        if (mOrderId == 0L)
            throw IllegalArgumentException("orderId is null")


        mView?.showProgress()
        val token = PreferencesManager.getTokenOrEmpty()
        mLastRequest = FinishDelivery.executeAsync(
                token,
                mOrderId,
                mDeliveryId,
                message,
                rating,
                mFinishDeliveryListener
        )

    }

    override fun cancelLastRequest() {
        mLastRequest?.cancel()
    }

    val mFinishDeliveryListener = object : FinishDelivery.ResponseListener {
        override fun onSuccess() {
            mView?.hideProgress()
            mView?.onSuccessFinished()
        }

        override fun onFailure(code: Int?, message: String?) {
            mView?.hideProgress()
            mView?.showError(message)
        }
    }

    override fun setupDeliveryId(deliveryId: Long) {
        mDeliveryId = deliveryId
    }

    override fun setupOrderId(orderId: Long) {
        mOrderId = orderId
    }
}