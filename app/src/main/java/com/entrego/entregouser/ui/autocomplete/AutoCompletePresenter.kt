package com.entrego.entregouser.ui.autocomplete

import android.os.Bundle
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.AutocompletePredictionBuffer
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

class AutoCompletePresenter : BaseMvpPresenter<AutocompleteContract.View>(),
        AutocompleteContract.Presenter,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    private var mGoogleApiClient: GoogleApiClient? = null
    private var mCameraBounds: LatLngBounds? = null
    //Panama city
    val mSouthWestLocation = LatLng(9.047261, -79.484000)
    val mNorthEastLocation = LatLng(10.047261, -78.484000)

    val mResultCallback = object : ResultCallback<AutocompletePredictionBuffer> {
        override fun onResult(resultBuffer: AutocompletePredictionBuffer) {
            val resultList = resultBuffer.toList()
            if (resultList.isEmpty())
                mView?.showFavoritesFragment()
            else
                mView?.hideFavoritesFragment()

            mView?.swapAutocompleteDataset(resultList)

        }
    }

    override fun buildGoogleApi() {
        mView?.getAppContext()?.let {
            mGoogleApiClient = GoogleApiClient.Builder(it)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build()
        }
    }

    override fun attachView(view: AutocompleteContract.View) {
        super.attachView(view)
        buildGoogleApi()
        mGoogleApiClient?.connect()
    }

    override fun detachView() {
        super.detachView()
        mGoogleApiClient?.disconnect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        mView?.showError(R.string.error_no_connection_with_google)
    }

    override fun onConnected(p0: Bundle?) {
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        query?.let {
            if (it.isNotEmpty())
                requestAddress(query)
            else
                mView?.showFavoritesFragment()
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            if (it.isNotEmpty())
                requestAddress(newText)
            else
                mView?.showFavoritesFragment()
        }
        return false
    }

    fun requestAddress(query: String) {
        val filter = AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build()

        val bounds = mCameraBounds
        val result = Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, query,
                bounds, filter)
        result.setResultCallback(mResultCallback)
    }

    override fun setBounds(bounds: LatLngBounds?) {
        mCameraBounds = bounds
    }

}