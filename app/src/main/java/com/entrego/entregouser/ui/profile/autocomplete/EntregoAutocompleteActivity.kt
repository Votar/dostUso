package com.entrego.entregouser.ui.profile.autocomplete

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.ui.profile.autocomplete.model.AutocompleteAddressAdapter
import com.entrego.entregouser.util.loading
import com.google.android.gms.location.places.AutocompletePrediction
import com.google.android.gms.maps.model.LatLngBounds
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_entrego_autocomplete.*


class EntregoAutocompleteActivity : BaseMvpActivity<AutocompleteContract.View,
        AutocompleteContract.Presenter>(),
        AutocompleteContract.View,
        AutocompleteAddressAdapter.OnItemClicked {

    companion object {
        const val RQT_CODE = 0x565
        private const val KEY_ADDRESS = "ext_k_address"
        private const val KEY_BOUNDS = "ext_k_bounds"
        private const val KEY_FILTER = "ext_k_filter"


        fun deserializeResult(intent: Intent): String {
            if (intent.hasExtra(KEY_ADDRESS)) {
                return intent.getStringExtra(KEY_ADDRESS)
            } else throw Exception("No result in this intent: ")
        }

        fun getBuilder(): IntentBuilder = IntentBuilder()

        class IntentBuilder {
            private @Nullable var bounds: LatLngBounds? = null

            fun setBounds(bounds: LatLngBounds?): IntentBuilder {
                this.bounds = bounds
                return this
            }
            fun build(ctx: Context): Intent {
                val intent = Intent(ctx, EntregoAutocompleteActivity::class.java)
                val gson = Gson()
                if (bounds != null) {
                    val json = gson.toJson(bounds, LatLngBounds::class.java)
                    intent.putExtra(KEY_BOUNDS, json)
                }
                return intent
            }
        }
    }


    override fun getRootView(): View? = activity_entrego_autocomplete

    override var mPresenter: AutocompleteContract.Presenter = AutoCompletePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrego_autocomplete)
        setSupportActionBar(entego_autocomplete_toolbar)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.autocomplete_title)

        setupLayouts()
        deserilizeExtras()
    }

    fun deserilizeExtras() {
        val gson = Gson()
        if (intent.hasExtra(KEY_BOUNDS)) {
            val json = intent.getStringExtra(KEY_BOUNDS)
            val bounds = gson.fromJson(json, LatLngBounds::class.java)
            mPresenter.setBounds(bounds)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.autocomplete_menu, menu)
        val item = menu?.findItem(R.id.action_search)
        entego_autocomplete_search_view.setMenuItem(item)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onStart() {
        super.onStart()
    }


    override fun onStop() {
        super.onStop()
    }

    fun setupLayouts() {
        entego_autocomplete_search_view.setVoiceSearch(false)

        entrego_autocomplete_results_recycler.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        entrego_autocomplete_results_recycler.itemAnimator = DefaultItemAnimator()

        entego_autocomplete_search_view.setOnQueryTextListener(mPresenter)
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        entrego_autocomplete_results_recycler.addItemDecoration(dividerItemDecoration)
        entrego_autocomplete_results_recycler.adapter = AutocompleteAddressAdapter(this)
    }

    var mProgress: ProgressDialog? = null
    override fun showProgress() {
        mProgress = ProgressDialog(this)
        mProgress?.loading()
    }

    override fun hideProgress() {
        mProgress?.dismiss()
    }

    override fun onClick(address: CharSequence) {
        setResult(Activity.RESULT_OK, Intent().putExtra(KEY_ADDRESS, address))
        finish()
    }

    override fun swapAutocompleteDataset(list: List<AutocompletePrediction>) {
        if (list.isEmpty())
            entrego_autocomplete_powered_by_google.visibility = View.GONE
        else
            entrego_autocomplete_powered_by_google.visibility = View.VISIBLE

        (entrego_autocomplete_results_recycler.adapter as AutocompleteAddressAdapter)
                .swapDataset(list)
    }

}
