package com.entregoya.entregouser.ui.history.list

import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entregoya.entregouser.mvp.view.IBaseMvpView
import com.entregoya.entregouser.ui.history.list.model.DeliveryListType


class DeliveryListContract {
    interface View : IBaseMvpView {
        fun showProgress()
        fun hideProgress()
        fun showList(resultList: List<EntregoDeliveryPreview>)
        fun showEmptyView()
        fun hideList()
        fun hideEmptyView()
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun setupType(type: DeliveryListType)
        fun requestUpdate()

    }
}