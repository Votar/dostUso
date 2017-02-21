package com.entrego.entregouser.ui.profile.history.list

import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView
import com.entrego.entregouser.ui.profile.history.list.model.DeliveryListType
import com.entrego.entregouser.ui.profile.history.list.model.EntregoDeliveryPreview


class DeliveryListContract {
    interface View : IBaseMvpView {
        fun showProgress()
        fun hideProgress()
        fun showList(resultList: List<EntregoDeliveryPreview>)
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun setupType(type: DeliveryListType)
        fun requestUpdate()

    }
}