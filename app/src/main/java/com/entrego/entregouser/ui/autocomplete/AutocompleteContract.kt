package com.entrego.entregouser.ui.autocomplete

import com.entrego.entregouser.mvp.presenter.IBaseMvpPresenter
import com.entrego.entregouser.mvp.view.IBaseMvpView
import com.entrego.entregouser.ui.profile.favorites.FavoritesController
import com.google.android.gms.location.places.AutocompletePrediction
import com.google.android.gms.maps.model.LatLngBounds
import com.miguelcatalan.materialsearchview.MaterialSearchView

interface AutocompleteContract {
    interface View : IBaseMvpView, FavoritesController {
        fun swapAutocompleteDataset(list: List<AutocompletePrediction>)
    }

    interface Presenter : IBaseMvpPresenter<View>,
            MaterialSearchView.OnQueryTextListener {

        fun setBounds(bounds :LatLngBounds?)
        fun buildGoogleApi()
    }
}