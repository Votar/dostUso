package com.entrego.entregouser.ui.payment

import com.entrego.entregouser.entity.common.PaymentMethodEntity
import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView
import com.entrego.entregouser.web.model.response.card.EntregoCreditCardEntity
import java.util.*

object PaymentMethodContract {
    interface View : IBaseMvpView {
        fun showAddCardActivity()
        fun showAddMoneyToWallet()
        fun loadDatasetPaymentMethods(list: LinkedList<Pair<PaymentMethodEntity, Boolean>>)
        fun showCardList(list: List<EntregoCreditCardEntity>)
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun requestCardList()
        fun savePaymentMethod(method: PaymentMethodEntity)
        fun loadPaymentMethod()
    }
}