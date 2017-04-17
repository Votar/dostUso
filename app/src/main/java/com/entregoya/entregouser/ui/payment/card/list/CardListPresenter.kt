package com.entregoya.entregouser.ui.payment.card.list

import com.entregoya.entregouser.mvp.presenter.BaseMvpPresenter
import com.entregoya.entregouser.storage.EntregoStorage
import com.entregoya.entregouser.ui.payment.card.list.model.CardListRequest
import com.entregoya.entregouser.web.api.ApiContract
import com.entregoya.entregouser.web.model.response.card.EntregoCreditCardEntity

class CardListPresenter : BaseMvpPresenter<CardListContract.View>(),
        CardListContract.Presenter {
    val token = EntregoStorage.getTokenOrEmpty()
    val mResponseListener = object : CardListRequest.CardListListener {
        override fun onSuccessCardList(resultList: Array<EntregoCreditCardEntity>) {

            if (resultList.isNotEmpty()) {
                mView?.hideEmptyView()
                mView?.setupCardList(resultList.toList())
            } else
                mView?.showEmptyView()

            mView?.hideProgress()
        }

        override fun onFailureCardList(code: Int?, message: String?) {

            when (code) {
                ApiContract.RESPONSE_INVALID_TOKEN -> mView?.onLogout()

                else -> mView?.showError(message)
            }
            mView?.hideProgress()
            mView?.showEmptyView()
        }
    }

    override fun requestUpdate() {
        mView?.showProgress()
        CardListRequest().executeAsync(token, mResponseListener)
    }
}