package com.entrego.entregouser.ui.profile.favorites

import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.storage.preferences.PreferencesManager
import com.entrego.entregouser.storage.realm.models.RealmAddressModel
import java.util.*

class FavoritesPresenter : BaseMvpPresenter<FavoritesContract.View>(),
        FavoritesContract.Presenter {
    override fun selectedHomeAddress(address: String) {
        if (address.isEmpty())
            mView?.showAutoCompleteForFavorite(FavoritesContract.RequestCodes.REQUEST_CODE_AUTOCOMPLETE_HOME)
        else
            mView?.selectedAddress(address)
    }

    override fun selectedWorkAddress(address: String) {
        if (address.isEmpty())
            mView?.showAutoCompleteForFavorite(FavoritesContract.RequestCodes.REQUEST_CODE_AUTOCOMPLETE_WORK)
        else
            mView?.selectedAddress(address)
    }

    var mLastNameFavoritePlace: String = ""

    override fun requestAddFavorites() {
        mView?.showAddFavorites()
    }

    override fun attachView(view: FavoritesContract.View) {
        super.attachView(view)
        val homeAddress = PreferencesManager.getHomeAddressOrEmpty()
        val workAddress = PreferencesManager.getWorkAddressOrEmpty()
        mView?.setupHomeAddress(homeAddress)
        mView?.setupWorkAddress(workAddress)
        requestFavorites()
    }


    override fun requestFavorites() {

        val listFavorites = EntregoStorage.getFavoritesList()
        val listRecentSearch = EntregoStorage.getRecentSearch()
                .sortedBy { it.createdTime }
                .takeLast(3)
                .reversed()

        ArrayList<RealmAddressModel>().apply {
            addAll(listRecentSearch)
            addAll(listFavorites)
            mView?.setupFavorites(this)
        }
    }

    override fun addNameFavoritePlace(name: String) {
        if (name.isNotEmpty()) {
            mView?.showAutoCompleteForFavorite(FavoritesContract.RequestCodes.REQUEST_CODE_AUTOCOMPLETE_FAVORITES)
            mLastNameFavoritePlace = name
        } else
            mView?.showError(R.string.error_empty_fields)
    }

    override fun addAddressFavoritePlace(address: String) {
        EntregoStorage.addFavoritePlace(mLastNameFavoritePlace, address)
        requestFavorites()
    }

}