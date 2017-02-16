package com.entrego.entregouser.ui.delivery.finish

import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.entrego.entregouser.ui.delivery.finish.model.FinishDelivery
import entrego.com.android.web.model.response.EntregoResponse
import retrofit2.Call


class FinishDeliveryPresenter : FinishDeliveryContract.Presenter,
        BaseMvpPresenter<FinishDeliveryContract.View>() {

    var mLastRequest: Call<EntregoResponse>? = null

    override fun sendDeliveryComment(message: String, rating: Float) {
        mView?.showProgress()
        mLastRequest = FinishDelivery.executeAsync("", "", 0f, mFinishDeliveryListener)
    }

    override fun detachView() {
        super.detachView()
        mView?.hideProgress()
    }

    override fun cancelLastRequest() {
        mLastRequest?.cancel()
    }

    val mFinishDeliveryListener = object : FinishDelivery.ResponseListener {
        override fun onSuccess() {
            mView?.hideProgress()
            mView?.onSuccessFinished()
        }

        override fun onFailure(message: String?, code: Int?) {
            mView?.hideProgress()
        }
    }
}