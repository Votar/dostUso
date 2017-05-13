package com.entregoya.entregouser.ui.payment.card.list

import com.entregoya.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entregoya.entregouser.mvp.view.IBaseMvpView
import com.entregoya.entregouser.web.model.response.card.EntregoCreditCardEntity

interface CardListContract {
    interface View: IBaseMvpView{
        fun setupCardList(resultList: List<EntregoCreditCardEntity>)
        fun showEmptyView()
        fun hideEmptyView()
        fun showProgress()
        fun hideProgress()
    }
    interface Presenter: IBaseMvpPresenter<View>{
        fun setupCards()
        fun requestUpdate()
    }
}