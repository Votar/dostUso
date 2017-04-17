package com.entrego.entregouser.ui.history.list

import com.entrego.entregouser.entity.back.EntregoDeliveryPreview
import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView
import com.entrego.entregouser.ui.history.list.model.DeliveryListType


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