package com.entregoya.entregouser.ui.favorites

import com.entregoya.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entregoya.entregouser.mvp.view.IBaseMvpView
import com.entregoya.entregouser.storage.realm.models.RealmAddressModel

interface FavoritesContract {
    interface View : IBaseMvpView {
        fun setupHomeAddress(address: String)
        fun setupWorkAddress(address: String)
        fun setupFavorites(list: List<RealmAddressModel>)
        fun selectedAddress(address: String)
        fun showProgress()
        fun hideProgress()
        fun showAddFavorites()
        fun showAutoCompleteForFavorite(requestCode: RequestCodes)
    }

    enum class RequestCodes(val value: Int) {
        REQUEST_CODE_AUTOCOMPLETE_FAVORITES(0x122),
        REQUEST_CODE_AUTOCOMPLETE_HOME(0x123),
        REQUEST_CODE_AUTOCOMPLETE_WORK(0x124)
    }

    interface Presenter : IBaseMvpPresenter<View> {
        fun selectedHomeAddress(address:String)
        fun selectedWorkAddress(address:String)
        fun requestFavorites()
        fun requestAddFavorites()
        fun addNameFavoritePlace(name: String)
        fun addAddressFavoritePlace(address: String)
    }
}