package com.entregoya.entregouser.ui.history.list

import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.entity.delivery.EntregoDeliveryStatuses
import com.entregoya.entregouser.mvp.presenter.BaseMvpPresenter
import com.entregoya.entregouser.storage.preferences.PreferencesManager
import com.entregoya.entregouser.ui.history.list.model.DeliveryListType
import com.entregoya.entregouser.ui.history.model.DeliveryListRequest
import com.entregoya.entregouser.web.api.ApiContract


class DeliveryListPresenter : BaseMvpPresenter<DeliveryListContract.View>(),
        DeliveryListContract.Presenter {

    //by default CURRENT
    var mListType: DeliveryListType = DeliveryListType.CURRENT

    override fun setupType(type: DeliveryListType) {
        mListType = type
    }

    override fun attachView(view: DeliveryListContract.View) {
        super.attachView(view)
    }

    override fun requestUpdate() {
        val token = PreferencesManager.getTokenOrEmpty()

        mView?.showProgress()
        DeliveryListRequest().requestAsync(token, mListType, mResponseListener)
    }

    val mResponseListener = object : DeliveryListRequest.ResponseListener {
        override fun onSuccessResponse(resultList: List<EntregoDeliveryPreview>) {
            mView?.hideProgress()
            when (mListType) {
                DeliveryListType.CURRENT -> {
                    val filteredList = resultList.filter {
                        it.status == EntregoDeliveryStatuses.PENDING
                                || it.status == EntregoDeliveryStatuses.ASSIGNED
                                || it.status == EntregoDeliveryStatuses.CONFIRMATION
                    }
                    if (filteredList.isEmpty()) {
                        mView?.hideList()
                        mView?.showEmptyView()
                    } else {
                        mView?.hideEmptyView()
                        mView?.showList(filteredList)
                    }
                }
                DeliveryListType.HISTORY -> {
                    val filteredList = resultList.filterNot {
                        it.status == EntregoDeliveryStatuses.PENDING
                                || it.status == EntregoDeliveryStatuses.ASSIGNED
                                || it.status == EntregoDeliveryStatuses.CONFIRMATION
                    }
                    if (filteredList.isEmpty()) {
                        mView?.hideList()
                        mView?.showEmptyView()
                    } else {
                        mView?.hideEmptyView()
                        mView?.showList(filteredList)
                    }
                }
            }
        }

        override fun onFailureResponse(code: Int?, message: String?) {
            mView?.hideProgress()
            when (code) {
                ApiContract.RESPONSE_INVALID_TOKEN -> {
                    mView?.showError(message)
                    mView?.onLogout()
                }
                else -> mView?.showError(message)

            }

        }
    }
}