package com.entrego.entregouser.ui.profile.favorites

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.view.BaseMvpFragment
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.storage.realm.models.RealmAddressModel
import com.entrego.entregouser.ui.profile.favorites.model.FavoritesAddressAdapter
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : BaseMvpFragment<FavoritesContract.View, FavoritesContract.Presenter>(),
        FavoritesContract.View {


    override fun showAddFavorites() {
        val alert = AlertDialog.Builder(activity)
        alert.setTitle(R.string.text_add_favorites_title)
        alert.setCancelable(false)
        val inflater = getLayoutInflater(arguments)
        val alertLayout = inflater.inflate(R.layout.fragment_add_favorite_place, null)
        val editTitle = alertLayout.findViewById(R.id.add_favorite_edit_place_title) as EditText
        alert.setView(alertLayout)

        alert.setNegativeButton(R.string.text_dismiss, { dialogInterface: DialogInterface, i: Int -> })
        alert.setPositiveButton(R.string.text_add, { dialogInterface: DialogInterface, i: Int ->
            run {
                mPresenter.addNameFavoritePlace(editTitle.text.toString())
            }
        })
        alert.show()
    }

    override var mPresenter: FavoritesContract.Presenter = FavoritesPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_favorites, container, false)

        return view
    }

    override fun onStart() {
        super.onStart()
        setupLayouts()
        mPresenter.requestFavorites()
    }

    fun setupLayouts() {
        favorites_recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(activity, LinearLayoutManager.VERTICAL)
        favorites_recycler.addItemDecoration(dividerItemDecoration)
        favorites_recycler.adapter = FavoritesAddressAdapter(mItemClickListener)
        favorites_value_home.text = EntregoStorage.getHomeAddressOrEmpty()
        favorites_value_work.text = EntregoStorage.getWorkAddressOrEmpty()
        favorites_home_rl.setOnClickListener {
            mPresenter.selectedHomeAddress(favorites_value_home.text.toString())
        }
        favorites_work_rl.setOnClickListener {
            mPresenter.selectedWorkAddress(favorites_value_work.text.toString())
        }
        favorites_add_fav.setOnClickListener {
            mPresenter.requestAddFavorites()
        }

    }

    val mItemClickListener = object : FavoritesAddressAdapter.OnItemClicked {
        override fun onClick(address: CharSequence) {
            (activity as? FavoritesController)?.selectedAddress(address.toString())
        }

    }

    override fun setupHomeAddress(address: String) {
        favorites_value_home.text = address
    }

    override fun setupWorkAddress(address: String) {
        favorites_value_work.text = address
    }

    override fun setupFavorites(list: List<RealmAddressModel>) {
        (favorites_recycler.adapter as? FavoritesAddressAdapter)
                ?.swapDataset(list)
    }

    override fun selectedAddress(address: String) {
        (activity as? FavoritesController)?.selectedAddress(address)
    }

    override fun showProgress() {
        favorites_swipe_refresh.isEnabled = true
        favorites_swipe_refresh.isRefreshing = true
    }

    override fun hideProgress() {
        favorites_swipe_refresh.isRefreshing = false
        favorites_swipe_refresh.isEnabled = false
    }


    override fun showAutoCompleteForFavorite(requestCode: FavoritesContract.RequestCodes) {
        val filter = AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build()
        val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                .setFilter(filter)
                .build(activity)
        startActivityForResult(intent, requestCode.value)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check that the result was from the autocomplete widget.
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                FavoritesContract.RequestCodes.REQUEST_CODE_AUTOCOMPLETE_FAVORITES.value -> data?.let {
                        val place = PlaceAutocomplete.getPlace(activity, data)
                        mPresenter.addAddressFavoritePlace(place.address.toString())
                    }
                FavoritesContract.RequestCodes.REQUEST_CODE_AUTOCOMPLETE_HOME.value -> data?.let {
                    val place = PlaceAutocomplete.getPlace(activity, data)
                    favorites_value_home.text = place.address
                    EntregoStorage.saveHomeAddress(place.address.toString())
                }

                FavoritesContract.RequestCodes.REQUEST_CODE_AUTOCOMPLETE_WORK.value -> data?.let {
                    val place = PlaceAutocomplete.getPlace(activity, data)
                    favorites_value_work.text = place.address
                    EntregoStorage.saveWorkAddress(place.address.toString())
                }
            }
        }
    }

}