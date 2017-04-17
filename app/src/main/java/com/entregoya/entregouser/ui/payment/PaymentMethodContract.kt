package com.entregoya.entregouser.ui.payment

import com.entregoya.entregouser.entity.common.PaymentMethodEntity
import com.entregoya.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entregoya.entregouser.mvp.view.IBaseMvpView
import com.entregoya.entregouser.web.model.response.card.EntregoCreditCardEntity
import java.util.*

object PaymentMethodContract {
    interface View : IBaseMvpView {
        fun showAddCardActivity()
        fun showAddMoneyToWallet()
        fun loadDatasetPaymentMethods(list: LinkedList<Pair<PaymentMethodEntity, Boolean>>)
        fun showCardList(list: List<EntregoCreditCardEntity>)
        fun showEditCardMenu()
        fun showDefaultMenu()
        fun showProgress()
        fun hideProgress()

    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun requestCardList()
        fun savePaymentMethod(method: PaymentMethodEntity)
        fun loadPaymentMethod()
        fun setupLastPaymentMethod(method: PaymentMethodEntity)
        fun deletePayment()
    }
}