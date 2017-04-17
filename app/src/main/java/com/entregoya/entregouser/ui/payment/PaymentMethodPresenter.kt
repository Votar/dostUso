package com.entregoya.entregouser.ui.payment

import com.entregoya.entregouser.R
import com.entregoya.entregouser.entity.common.PaymentMethodEntity
import com.entregoya.entregouser.entity.common.PaymentMethodType
import com.entregoya.entregouser.mvp.presenter.BaseMvpPresenter
import com.entregoya.entregouser.storage.EntregoStorage
import com.entregoya.entregouser.ui.payment.card.list.model.CardListRequest
import com.entregoya.entregouser.ui.payment.model.DeleteCardRequest
import com.entregoya.entregouser.web.api.ApiContract
import com.entregoya.entregouser.web.model.response.card.EntregoCreditCardEntity
import java.util.*


class PaymentMethodPresenter : PaymentMethodContract.Presenter,
        BaseMvpPresenter<PaymentMethodContract.View>() {
    val mToken = EntregoStorage.getTokenOrEmpty()


    var mLastPaymentMethod: PaymentMethodEntity? = null

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
        val result = EntregoStorage.setDefaultPaymentMethod(method)
        if (result)
            mView?.showMessage(R.string.text_success_save)
        else
            mView?.showError(R.string.error_storage)
    }

    override fun deletePayment() {
        if (mLastPaymentMethod?.type != PaymentMethodType.CARD || mLastPaymentMethod == null)
            throw IllegalStateException("This action is not allow with this payment method")
        else {

            val mDeleteCardListener = object : DeleteCardRequest.DeleteCardRequestListener {
                override fun onSuccessDeleteCardRequest() {
                    mView?.hideProgress()
                    mView?.showMessage(R.string.text_success_delete_card)

                    val currentDefault = EntregoStorage.getDefaultPaymentMethod()
                    if (currentDefault.card?.token?.equals(mLastPaymentMethod?.card?.token) == true) {
                        val default = PaymentMethodEntity(PaymentMethodType.CASH)
                        EntregoStorage.setDefaultPaymentMethod(default)

                    }
                    loadPaymentMethod()
                    requestCardList()
                }

                override fun onFailureDeleteCardRequest(code: Int?, message: String?) {
                    mView?.hideProgress()
                    mView?.showError(message)
                }

            }

            mLastPaymentMethod?.card?.token?.also {
                mView?.showProgress()
                DeleteCardRequest().executeAsync(mToken,
                        it,
                        mDeleteCardListener
                )
            }
        }
    }

    override fun loadPaymentMethod() {
        val default = EntregoStorage.getDefaultPaymentMethod()
        loadListWithSelectedPaymentMethod(default)
    }

    override fun setupLastPaymentMethod(method: PaymentMethodEntity) {
        mLastPaymentMethod = method
        if (mLastPaymentMethod?.type == PaymentMethodType.CARD)
            mView?.showEditCardMenu()
        else
            mView?.showDefaultMenu()
    }

    private fun loadListWithSelectedPaymentMethod(default: PaymentMethodEntity) {
        if (default.type == PaymentMethodType.CARD) {
            mLastPaymentMethod = default
            mView?.showEditCardMenu()
        } else mView?.showDefaultMenu()
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
