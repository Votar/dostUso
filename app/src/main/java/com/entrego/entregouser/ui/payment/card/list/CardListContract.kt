package com.entrego.entregouser.ui.payment.card.list

import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView
import com.entrego.entregouser.web.model.response.card.EntregoCreditCardEntity

interface CardListContract {
    interface View: IBaseMvpView{
        fun setupCardList(resultList: List<EntregoCreditCardEntity>)
        fun showEmptyView()
        fun hideEmptyView()
        fun showProgress()
        fun hideProgress()
    }
    interface Presenter: IBaseMvpPresenter<View>{
        fun requestUpdate()
    }
}