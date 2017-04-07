package com.entrego.entregouser.ui.payment

import com.entrego.entregouser.R
import com.entrego.entregouser.entity.common.PaymentMethodEntity
import com.entrego.entregouser.entity.common.PaymentMethodType
import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.ui.payment.card.list.model.CardListRequest
import com.entrego.entregouser.web.api.ApiContract
import com.entrego.entregouser.web.model.response.card.EntregoCreditCardEntity
import java.util.*


class PaymentMethodPresenter : PaymentMethodContract.Presenter,
        BaseMvpPresenter<PaymentMethodContract.View>() {
    val mToken = EntregoStorage.getTokenOrEmpty()

    val mResponseListener = object : CardListRequest.CardListListener {
        override fun onSuccessCardList(resultList: Array<EntregoCreditCardEntity>) {

            if (resultList.isNotEmpty()) {
                mView?.showCardList(resultList.toList())
            }
        }

        override fun onFailureCardList(code: Int?, message: String?) {

            when (code) {
                ApiContract.RESPONSE_INVALID_TOKEN -> mView?.onLogout()
                else -> mView?.showError(message)
            }
        }
    }

    override fun attachView(view: PaymentMethodContract.View) {
        super.attachView(view)
    }

    override fun requestCardList() {
        CardListRequest().executeAsync(mToken, mResponseListener)
    }

    override fun savePaymentMethod(method: PaymentMethodEntity) {
        val result = EntregoStorage.saveDefaultPaymentMethod(method)
        if (result)
            mView?.showMessage(R.string.text_success_save)
        else
            mView?.showError(R.string.error_storage)
    }

    override fun loadPaymentMethod() {
        val default = EntregoStorage.getDefaultPaymentMethod()
        loadListWithSelectedPaymentMethod(default)
    }

    private fun loadListWithSelectedPaymentMethod(default: PaymentMethodEntity) {
        val listMethods = LinkedList<Pair<PaymentMethodEntity, Boolean>>()
        PaymentMethodType.values().forEach {

            if (default.type == it)
                listMethods.add(Pair(default, true))
            else if (it != PaymentMethodType.CARD)
                listMethods.add(Pair(PaymentMethodEntity(it), false))
        }
        mView?.loadDatasetPaymentMethods(listMethods)
    }
}
