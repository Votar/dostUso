package com.entrego.entregouser.ui.delivery.finish

import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.entrego.entregouser.storage.preferences.PreferencesManager
import com.entrego.entregouser.ui.delivery.finish.model.FinishDelivery
import com.entrego.entregouser.web.model.response.BaseEntregoResponse
import retrofit2.Call


class FinishDeliveryPresenter : FinishDeliveryContract.Presenter,
        BaseMvpPresenter<FinishDeliveryContract.View>() {


    var mLastRequest: Call<BaseEntregoResponse>? = null
    var mDeliveryId: Long? = null
    override fun sendDeliveryComment(message: String, rating: Float) {

        if (mDeliveryId == null)
            throw Exception("DeliveryId is null")

        mDeliveryId?.let {
            mView?.showProgress()
            val token = PreferencesManager.getTokenOrEmpty()
            mLastRequest = FinishDelivery.executeAsync(token, it, message, rating, mFinishDeliveryListener)
        }


    }

    override fun cancelLastRequest() {
        mLastRequest?.cancel()
    }

    val mFinishDeliveryListener = object : FinishDelivery.ResponseListener {
        override fun onSuccess() {
            mView?.onSuccessFinished()
        }

        override fun onFailure(message: String?, code: Int?) {
            mView?.showError(message)
        }
    }

    override fun setupDeliveryId(deliveryId: Long) {
        mDeliveryId = deliveryId
    }
}