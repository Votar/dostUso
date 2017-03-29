package com.entrego.entregouser.ui.payment.card

import android.content.Intent
import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView


object AddCardContract {

    enum class FieldType {
        NUMBER,
        EXPIRY,
        CVV
    }

    interface View : IBaseMvpView {
        fun showProgress()
        fun hideProgress()
        fun setupCardNumber(number: String)
        fun setupExpire(expiry: String)
        fun setupCvv(cvv: String)
        fun setError(field: FieldType, strResId: Int)
        fun setError(field: FieldType, message: String)
        fun showScanCardActivity(requestCode: Int, intent: Intent?)
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun saveCard(name: String, number: String, expiry: String, cvv: String)
        fun showScanCard()
        fun parseStartForResult(requestCode: Int, resultCode: Int, data: Intent?)
    }
}