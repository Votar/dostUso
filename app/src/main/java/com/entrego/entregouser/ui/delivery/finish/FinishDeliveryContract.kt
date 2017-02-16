package com.entrego.entregouser.ui.delivery.finish

import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView


object FinishDeliveryContract {

    interface View : IBaseMvpView {
        fun setCommentError(strResId: Int)
        fun setCommentError(message: String)
        fun onSuccessFinished()
        fun CleanErrorFields()
    }


    interface Presenter : IBaseMvpPresenter<View> {
        fun sendDeliveryComment(message: String, rating: Float)
        fun cancelLastRequest()
    }
}