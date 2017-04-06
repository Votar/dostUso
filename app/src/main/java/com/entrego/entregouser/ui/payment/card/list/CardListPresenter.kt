package com.entrego.entregouser.ui.payment.card.list

import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.ui.payment.card.list.model.CardListRequest
import com.entrego.entregouser.web.model.response.card.EntregoCreditCardEntity

class CardListPresenter : BaseMvpPresenter<CardListContract.View>(),
        CardListContract.Presenter {
    val token = EntregoStorage.getTokenOrEmpty()
    val mResponseListener = object : CardListRequest.CardListListener {
        override fun onSuccessCardList(resultList: Array<EntregoCreditCardEntity>) {

            if (resultList.isNotEmpty()){
                mView?.hideEmptyView()
                mView?.setupCardList(resultList.toList())
            }
            else
                mView?.showEmptyView()

            mView?.hideProgress()
        }

        override fun onFailureCardList(code: Int?, message: String?) {
            mView?.showError(message)
            mView?.hideProgress()
            mView?.showEmptyView()
        }
    }

    override fun requestUpdate() {
        mView?.showProgress()
        CardListRequest().executeAsync(token, mResponseListener)
    }
}