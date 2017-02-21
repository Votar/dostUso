package com.entrego.entregouser.ui.profile.history.list

import com.entrego.entregouser.entity.delivery.EntregoDelivery
import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.entrego.entregouser.storage.preferences.PreferencesManager
import com.entrego.entregouser.ui.profile.history.list.model.DeliveryListRequest
import com.entrego.entregouser.ui.profile.history.list.model.DeliveryListType
import com.entrego.entregouser.ui.profile.history.list.model.EntregoDeliveryPreview


class DeliveryListPresenter : BaseMvpPresenter<DeliveryListContract.View>(),
        DeliveryListContract.Presenter {

    //by default CURRENT
    var mListType: DeliveryListType = DeliveryListType.CURRENT

    override fun setupType(type: DeliveryListType) {
        mListType = type
    }

    override fun attachView(view: DeliveryListContract.View) {
        super.attachView(view)
        requestUpdate()
    }

    override fun requestUpdate() {
        val token = PreferencesManager.getTokenOrEmpty()

        mView?.showProgress()
        DeliveryListRequest().requestAsync(token, mListType, mResponseListener)
    }

    val mResponseListener = object : DeliveryListRequest.ResponseListener {
        override fun onSuccessResponse(resultList: List<EntregoDeliveryPreview>) {
            mView?.hideProgress()
            mView?.showList(resultList)
        }

        override fun onFailureResponse(code: Int?, message: String?) {
            mView?.hideProgress()
            mView?.showError(message)
        }
    }
}