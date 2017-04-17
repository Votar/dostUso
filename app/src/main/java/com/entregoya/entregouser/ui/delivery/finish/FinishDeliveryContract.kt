package com.entregoya.entregouser.ui.delivery.finish

import com.entregoya.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entregoya.entregouser.mvp.view.IBaseMvpView


object FinishDeliveryContract {

    interface View : IBaseMvpView {
        fun setCommentError(strResId: Int)
        fun setCommentError(message: String)
        fun onSuccessFinished()
        fun CleanErrorFields()
        fun showProgress()
        fun hideProgress()
    }


    interface Presenter : IBaseMvpPresenter<View> {
        fun sendDeliveryComment(message: String, rating: Int)
        fun setupDeliveryId(deliveryId:Long)
        fun cancelLastRequest()
        fun setupOrderId(orderId:Long)

    }
}