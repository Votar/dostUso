package com.entrego.entregouser.ui.payment.card.add

import android.content.Intent
import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView


object AddCardContract {

    enum class FieldType(val value: String) {
        NAME("name"),
        NUMBER("number"),
        MONTH("month"),
        YEAR("year"),
        CVV("cvv");

        companion object {

            fun fromString(str: String): FieldType {

                values().forEach {
                    if (it.value.equals(str, true))
                        return it
                }
                throw IllegalStateException("Could not find $str in FieldType")
            }
        }
    }

    interface View : IBaseMvpView {
        fun showProgress()
        fun hideProgress()
        fun setupCardNumber(number: String)
        fun setupExpire(month: String, year: String)
        fun setupCvv(cvv: String)
        fun setError(field: FieldType, strResId: Int)
        fun setError(field: FieldType, message: String)
        fun showScanCardActivity(requestCode: Int, intent: Intent?)
        fun disableInputLayoutsError()
        fun enableInputLayoutsError()
        fun logout()
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun saveCard(name: String, number: String, month: String, year: String, cvv: String)
        fun showScanCard()
        fun parseStartForResult(requestCode: Int, resultCode: Int, data: Intent?)
    }
}